package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import domain.PagingVO;
import handler.PagingHandler;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//로그 객체 생성
	// 모든 클래스 상단에 작성
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);	
	// RequestDispatcher
	// jsp 에서 받은 요청을 처리한 후 그 결과를 다른 jsp로 보내주는 역할을 하는 객체
	private RequestDispatcher rdp;
	// jsp (목적지) 주소를 저장하는 변수
	private String destPage;
	// isOk 변수 DB 구문값 체크를 위해 저장하는 변수
	private int isOk;
	
	// service 연결 인터페이스
	private BoardService bsv; // 인터페이스 => 구현체 BoardServiceImpl
	
	
    public BoardController() {	
    	bsv = new BoardServiceImpl();
    
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get / post로 오는 모든 요청을 service에서 처리
		log.info(">>> BoardController Service method in Test!!");
		
		// post로 들어오는 객체는 한글깨짐발생 => 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 응답 객체의 contentType 설정 => jsp 파일로 응답 (동기 방식)
		response.setContentType("text/html; charset=UTF-8");
		
		// jsp에서 요청하는 주소를 받는 객체 /brd/register
		String uri = request.getRequestURI();
		log.info(">>> {}", uri);
		
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(" >>> {}", path);
		
		switch(path) {
		case "register" :
			destPage = "/board/register.jsp"; // webapp 기준
			break;
		case "insert" :
			try {
				// title, writer, content
				String title = request.getParameter("title"); // name 값으로 추출
				String writer = request.getParameter("writer"); 
				String content = request.getParameter("content");
				
				// DB에 등록하기 위한 객체 생성
				Board b = new Board(title, writer, content); 
				log.info(" >>> board {}", b);
				
				// boardService 객체로 해당 객체를 전달
				isOk = bsv.insert(b);
				
				// DB에서 저장이 잘 완료되면 1이 리턴, 안되면 0이 리턴
				log.info(" >>> insert {}", (isOk > 0)? "성공" : "실패");
				
				// 처리 후 보내야하는 주소
				destPage = "list";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "list" :
			try {
				// DB의 전체 리스트를 요청
				// 페이징을 해서 리스트 생성
				// List<Board> list = bsv.getList();
				PagingVO pgvo = new PagingVO(); // pageNo=1, qty=10
				
				if(request.getParameter("pageNo") != null) {
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					pgvo = new PagingVO(pageNo, qty);
				}
				
				
				// select * from board order by bno desc limit #{pageStart}, #{qty}
				List<Board> list = bsv.getPageList(pgvo);
				log.info(" >>> list {}", list);
				
				int totalCount = bsv.getTotal();
				
				PagingHandler ph = new PagingHandler(pgvo, totalCount);
				log.info(" >>> ph >> {}", ph);
				// list를 jsp로 보내기
				request.setAttribute("list", list);
				request.setAttribute("ph", ph);
				// info의 첫요소는 문자 그 후에 객체
				destPage="/board/list.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "detail": case "modify" :
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				Board board = bsv.getDetail(bno);
				log.info(" >>> detail {}", board);
				request.setAttribute("b", board);
				destPage="/board/"+ path +".jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "update" : 
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				Board b = new Board(bno, title, content);
				log.info(" >>> update {}", b);
				isOk = bsv.update(b);
				
				log.info(" >>> update {}", (isOk > 0)? "성공" : "실패");
				//	response.sendRedirect("detail?bno="+bno);
				destPage = "detail?bno"+bno;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "remove" : 
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				isOk = bsv.delete(bno);
				log.info(" >>> remove {}", (isOk > 0)? "성공" : "실패");
				destPage="list";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		}
		
		// 처리가 완료된 만들어진 응답객체를 jsp화면으로 보내기
		// RequestDispatcher : 데이터를 전달하는 객체 / 어디로 destPage로...
		rdp = request.getRequestDispatcher(destPage);
		// 요청한 객체를 가지고 destPage에 적힌 주소로 이동 (forward)
		rdp.forward(request, response);
		
		// forward : 처리 주체가 WAS(톰켓) => 처리 속도가 빠름
		// => 처음 요청 URL이 변경되지 않음. 
		//	/brd/insert 고정되어서 새로고침시 계속 추가
		//  request.getRequestDispatcher("list").forward(request, response)
		
		// redirect : 처리 주체가 Web Browser (was에서 받은 response를 새로운 URL로 다시 Request)
		// 	=> 요청에 따라 다른 URL로 변경됨.		
	}
	
//	private void resForword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// 처리가 완료된 만들어진 응답객체를 jsp화면으로 보내기
//				// RequestDispatcher : 데이터를 전달하는 객체 / 어디로 destPage로...
//				rdp = request.getRequestDispatcher(destPage);
//				// 요청한 객체를 가지고 destPage에 적힌 주소로 이동 (forward)
//				rdp.forward(request, response);
//	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get으로 오는 요청에 대한 처리를 하고, HTML파일을 생성하여 response 객체를 생성하여 jsp로 전송
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get 으로 보냄
		// doGet(request, response);
		service(request, response);
	}

}
