package database;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;

@WebServlet("/selectDistance")
public class SelectDistance extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 클라이언트에서 post로 받아온 위도, 경도 가지고 옴
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        // 응답 콘텐츠 타입, 인코딩 설정(한글 깨짐 방지)
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (lat != null && lnt != null) {
            double latitude = Double.parseDouble(lat); // String으로 받아온 값 double 로 변경
            double longitude = Double.parseDouble(lnt);

            PreparedStatement statement = null; // sql prepared로 실행하기 위해 객체 선언
            ResultSet rs = null; // 쿼리 결과 저장하기 위한 객체 선언

            try (Connection connection = DatabaseConnection.getConnection()) { //2. 커넥션 객체 생성
                // try-with-resources 문법: close 없이 자동으로 닫히게 하는 역할
                // 리소스 해제시 발생하는 오류도 catch 에서 잡을 수 있음

                // SELECT 구문 - 허버사인 공식
                String sql = "SELECT\n" +
                        "    FORMAT(6371 * Acos(Cos(Radians(?)) * Cos(Radians(LAT)) * Cos(Radians(LNT) - Radians(?)) + Sin(Radians(?)) * Sin(Radians(LAT))), 4)\n" +
                        "        AS DISTANCE,\n" +
                        "    X_SWIFI_MGR_NO,\n" +
                        "    X_SWIFI_WRDOFC,\n" +
                        "    X_SWIFI_MAIN_NM,\n" +
                        "    X_SWIFI_ADRES1,\n" +
                        "    X_SWIFI_ADRES2,\n" +
                        "    X_SWIFI_INSTL_FLOOR,\n" +
                        "    X_SWIFI_INSTL_TY,\n" +
                        "    X_SWIFI_INSTL_MBY,\n" +
                        "    X_SWIFI_SVC_SE,\n" +
                        "    X_SWIFI_CMCWR,\n" +
                        "    X_SWIFI_CNSTC_YEAR,\n" +
                        "    X_SWIFI_INOUT_DOOR,\n" +
                        "    X_SWIFI_REMARS3,\n" +
                        "    X_SWIFI_REMARS3,\n" +
                        "    LAT,\n" +
                        "    LNT,\n" +
                        "    WORK_DTTM\n" +
                        "\n" +
                        "FROM wifi\n" +
                        "ORDER BY distance\n" +
                        "LIMIT 20;";

                statement = connection.prepareStatement(sql);
                statement.setDouble(1, latitude);
                statement.setDouble(2, longitude);
                statement.setDouble(3, latitude);
                rs = statement.executeQuery(); // 쿼리 수행

                List<WIFILocation> locationList = new ArrayList<>();
                System.out.println("locationlist " + locationList);

                while (rs.next()) { // 객체에서 다음 행이 있는지 확인 (true, false 반환)
                    // 데이터베이스에서 각 행 가져오기
                    WIFILocation location = new WIFILocation();
                    location.setMgrNo(rs.getString("X_SWIFI_MGR_NO") != null ? rs.getString("X_SWIFI_MGR_NO") : "");
                    location.setWrdofc(rs.getString("X_SWIFI_WRDOFC") != null ? rs.getString("X_SWIFI_WRDOFC") : "");
                    location.setMainNm(rs.getString("X_SWIFI_MAIN_NM") != null ? rs.getString("X_SWIFI_MAIN_NM") : "");
                    location.setAdres1(rs.getString("X_SWIFI_ADRES1") != null ? rs.getString("X_SWIFI_ADRES1") : "");
                    location.setAdres2(rs.getString("X_SWIFI_ADRES2") != null ? rs.getString("X_SWIFI_ADRES2") : "");
                    location.setInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR") != null ? rs.getString("X_SWIFI_INSTL_FLOOR") : "");
                    location.setInstlTy(rs.getString("X_SWIFI_INSTL_TY") != null ? rs.getString("X_SWIFI_INSTL_TY") : "");
                    location.setInstlMby(rs.getString("X_SWIFI_INSTL_MBY") != null ? rs.getString("X_SWIFI_INSTL_MBY") : "");
                    location.setSvcSe(rs.getString("X_SWIFI_SVC_SE") != null ? rs.getString("X_SWIFI_SVC_SE") : "");
                    location.setCmcwr(rs.getString("X_SWIFI_CMCWR") != null ? rs.getString("X_SWIFI_CMCWR") : "");
                    location.setCnstcYear(rs.getString("X_SWIFI_CNSTC_YEAR") != null ? rs.getString("X_SWIFI_CNSTC_YEAR") : "");
                    location.setInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR") != null ? rs.getString("X_SWIFI_INOUT_DOOR") : "");
                    location.setRemars3(rs.getString("X_SWIFI_REMARS3") != null ? rs.getString("X_SWIFI_REMARS3") : "");
                    location.setLat(rs.getDouble("LAT"));
                    location.setLnt(rs.getDouble("LNT"));
                    location.setWorkDttm(rs.getString("WORK_DTTM") != null ? rs.getString("WORK_DTTM") : "");
                    location.setDistance(rs.getDouble("distance"));
                    locationList.add(location);
                }
                request.setAttribute("locationList", locationList);

                // 조회 위도 경도, 현재시간 history 테이블에 INSERT
                String insertSql = "INSERT INTO history(LAT, LNT, RTV_TIME) VALUES (?, ?, ?)";

                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setDouble(1, latitude);
                    insertStmt.setDouble(2, longitude);
                    insertStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 현재 시간
                    insertStmt.executeUpdate();
                }

                // 요청과 응답 객체를 "/display_wifi.jsp" 페이지로 전달하여 서버 측에서 페이지를 이동
                request.getRequestDispatcher("/display_wifi.jsp").forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
                // 객체에 다음 행이 없을 경우 에러페이지로 이동해 에러 메세지 노출
                request.setAttribute("error", "가까운 위치 조회에 실패했습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);

                //6. 객체 연결 해제(close)
            } finally {
                if (rs != null) try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (statement != null) try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (statement != null) try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else { // 위도 경도 없을 경우
            out.println("위도 경도 항목은 필수 값 입니다.<br>");
        }
    }

    @Getter
    @Setter
    public class WIFILocation {
        private String mgrNo;
        private String wrdofc;
        private String mainNm;
        private String adres1;
        private String adres2;
        private String instlFloor;
        private String instlTy;
        private String instlMby;
        private String svcSe;
        private String cmcwr;
        private String cnstcYear;
        private String inoutDoor;
        private String remars3;
        private double lat;
        private double lnt;
        private String workDttm;
        private double distance;
    }
}