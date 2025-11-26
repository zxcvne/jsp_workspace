package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
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
				destPage = "/index.jsp";
				
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
		
	}

	
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
