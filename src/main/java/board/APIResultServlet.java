package board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/apiResult")
public class APIResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // APIResult 클래스의 printResult 메서드 호출
        ResultDto result = APIResult.printResult();

        // ResultDto 객체를 request 속성에 설정
        request.setAttribute("result", result);

        // JSP 페이지로 포워드
        request.getRequestDispatcher("/display_wifi.jsp").forward(request, response);
    }
}
