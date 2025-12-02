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

import domain.User;
import service.UserService;
import service.UserServiceImpl;

@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	// 요청에 대한 응답 데이터를 jsp 전송하는 역할
	private RequestDispatcher rdp;
	private String destPage;
	
	private UserService usv;

    public UserController() {
        usv = new UserServiceImpl();
    }
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 실제 처리 메서드
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(" >>> path {}", path);
		
		switch(path) {		
		case "register" :
			destPage = "/member/register.jsp";
			break;
		case "insert" :
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				
				User user = new User(id, pwd, email, phone);
				int isOk = usv.insert(user);
				
				log.info(" >>>> insert {}", (isOk>0)? "성공" : "실패");
				destPage = "/index.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
