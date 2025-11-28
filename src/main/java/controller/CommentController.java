package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Board;
import domain.Comment;
import service.CommentService;
import service.CommentServiceImpl;

@WebServlet("/cmt/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final Logger log = LoggerFactory.getLogger(CommentController.class);
	
    // 비동기 방식의 요청을 처리하는 컨트롤러.
    // 데이터를 요청한 곳으로 결과를 보냄
    // 객체형태로(JSON), 또는 텍스트 형태로 보냄
    // RequestDispatcher / destPage / setContentType => 필요 없음.
    
    private CommentService csv;
    
    public CommentController() {
        csv = new CommentServiceImpl();
    }
    

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI(); // /cmt/insert
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(" >>> path >>> {}", path);
		
		switch(path) {
		case "post":
			try {
				log.info("post ...");
				// 동기 방식 => request.getParameter // 객체를 읽어들임.
				// 비동기 방식 => 파일 입출력 처럼 읽고(Reader) 쓰기(Writer)
				// request.getReader / response.getWriter()
//				BufferedReader br = request.getReader();
//				StringBuffer sb = new StringBuffer();
//				String line= "";
//				while((line=br.readLine()) != null) {
//					sb.append(line);
//				}
//				log.info(" >>> sb >> {}", sb.toString());
				// {"bno":"5","writer":"111","content":"111"}
				// String -> 객체 형태로 parser => JSONObject => key:value => Comment
				// 라이브러리 json-simple-1.1.1 JSON parser 라이브러리
				JSONParser parser = new JSONParser();
//				JSONObject jsonobj = (JSONObject)parser.parse(sb.toString()); // return Object
				JSONObject jsonobj = (JSONObject)parser.parse(request.getReader()); // return Object
				log.info(" >>> jsonobj >> {}", jsonobj);
			
				
				int bno = Integer.parseInt(jsonobj.get("bno").toString()); // object => string -> int
				String writer = jsonobj.get("writer").toString();
				String content = jsonobj.get("content").toString();
				
				Comment c = new Comment(bno, writer, content);
				int isOk = csv.insert(c);
				
				log.info(" >> insert >> {}", (isOk>0)? "성공" : "실패");
				
				PrintWriter out = response.getWriter();
				out.print(isOk);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			break;
		case "list" :
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				log.info(" >>> bno >> {}", bno);
				
				List<Comment> list = csv.getList(bno);
				
				log.info(" >>> cmt list >> {}", list);
				
				
				// JSONArray[] => add / JSONObject [] => put
				// List<Comment> list => json 형식으로 변환
				// [{..}, {..}]
				JSONArray jsonArray = new JSONArray(); // []
				for(Comment c : list) {
					JSONObject obj = new JSONObject(); // {} => map
					obj.put("cno", c.getCno());
					obj.put("bno", c.getBno());
					obj.put("writer", c.getWriter());
					obj.put("content", c.getContent());
					obj.put("regdate", c.getRegdate());
					
					jsonArray.add(obj);
				}
				log.info(" >>> jsonArray >> {}", jsonArray);
				
				// jsonArray => String 변환을 해야 전송이 가능.
				 String jsonData = jsonArray.toJSONString();
				 
				 PrintWriter out = response.getWriter();
				 out.print(jsonData);
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "remove" :
			try {
				// ? queryString 은 getParameter로 가져올 수 있음
				int cno = Integer.parseInt(request.getParameter("cno"));
				int isOk = csv.delete(cno);
				log.info(" >> remove >> {}", (isOk>0)? "성공" : "실패");
				PrintWriter out = response.getWriter();
				out.print(isOk);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "modify" :	
			try {
				BufferedReader br = request.getReader(); // 받기
				JSONParser parser = new JSONParser();
				JSONObject jsonobj = (JSONObject)parser.parse(br); 
				
				int cno = Integer.parseInt(jsonobj.get("cno").toString()); 
				String content = jsonobj.get("content").toString();
				
				Comment c = new Comment(cno, content);
				log.info(" >>> modify cmt >> {}", c);
				int isOk = csv.update(c);
				
				log.info(" >> modify >> {}", (isOk>0)? "성공" : "실패");
				
				PrintWriter out = response.getWriter();
				out.print(isOk);	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		}
			
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get으로 들어오는 요청을 처리 => service를 호출하여 처리
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// insert/update
		service(request, response);
	}

}
