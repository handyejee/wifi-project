package board;

import database.HistoryDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/historyServlet")
public class HistoryServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // selectHistory 메서드 실행
        List<String[]> historyList = HistoryDAO.selectHistory();

        System.out.println("HistoryListSize: " + historyList.size());
        for (String[] row : historyList) {
            System.out.println(Arrays.toString(row));
        }

        // 조회된 데이터 request 객체에 설정
        request.setAttribute("historyList", historyList);

        // JSP로 보내주기
        request.getRequestDispatcher("/history_wifi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // 데이터베이스에서 deleteHistory 호출
        String id = request.getParameter("id");
        System.out.println("ID to delete: " + id);  // 추가된 로그

        try {
            // deleteHistory 메서드 실행
            boolean isDeleted = HistoryDAO.deleteHistory(id);
            System.out.println("isDeleted: " + isDeleted); // 로그

            if(isDeleted) {
                response.sendRedirect("historyServlet");
            } else {
                response.getWriter().println("삭제 실패");
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
