package board;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdbc.MySQLConnector;
import users.vo.MemberBean;

/**
 * Servlet implementation class boardInsertProc
 */
@WebServlet("/board/InsertProc.do")
public class boardInsertProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public boardInsertProc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =request.getSession();
		MemberBean memberVO = (MemberBean)session.getAttribute("memberVO");

		String writer =  memberVO.getUserid(); // 작성자

		String title = request.getParameter("boardTitle");  // 제목
		
		String content = request.getParameter("boardContent"); // 내용
		
		String rating = request.getParameter("tmp"); // 점수
		
		String dramaName = request.getParameter("dramaName");
		
		
		MySQLConnector mysql = new MySQLConnector();
		Connection conn = mysql.getConnection();
		
		String query = "insert into board (writer,title,content,rating,dramaName,time) values ('%writer%','%title%','%content%','%rating%','%dramaName%',SYSDATE())";
		
		query = query.replace("%writer%", writer);
		query = query.replace("%title%", title);
		query = query.replace("%content%", content);
		query = query.replace("%rating%", rating);
		query = query.replace("%dramaName%", dramaName);
		
		if(mysql.insert(query, conn)) {
			System.out.println("글쓰기가 성공하였습니다.");
			response.sendRedirect("main.jsp");
		}else {
			System.out.println(query);
			System.out.println("글쓰기가 실패하였습니다.");
			response.sendRedirect("/board/InsertForm.do");

		}
		

		
	}

}
