package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    public static List<String[]> selectHistory() {
        List<String[]> historyList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet result;

        try (Connection connection = DatabaseConnection.getConnection()) { //2. 커넥션 객체 생성
            // try-with-resources 문법: close 없이 자동으로 닫히게 하는 역할
            // 리소스 해제시 발생하는 오류도 catch 에서 잡을 수 있음

            // SELECT 구문
            String sql = "SELECT * FROM history\n" +
                            "ORDER BY ID DESC;";

            statement = connection.prepareStatement(sql);
            result = statement.executeQuery(); // 쿼리 수행

            // 조회 결과 담기
            while (result.next()){
                String[] row = new String[4];

                row[0] = result.getString("ID");
                row[1] = result.getString("LAT");
                row[2] = result.getString("LNT");
                row[3] = result.getString("RTV_TIME");
                historyList.add(row); // row 배열 값을 historyList 리스트에 담아줌

                // 디버깅을 위해 각 행 출력
                System.out.println(java.util.Arrays.toString(row));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return historyList;
    }

    public static boolean deleteHistory(String id) throws SQLException {
        // DB 커넥션 연결
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "DELETE FROM history WHERE ID = ?";

            // prepared문 사용하기 위해 객체 선언
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id); // ? id 값 전달
            int rowsAffected = statement.executeUpdate(); // 쿼리 수행

            return rowsAffected > 0; // 쿼리 수행 여부 boolean 값으로 리턴

        } catch (SQLException e){ // 예외 발생시 예외 프린트 해주고 return false
            e.printStackTrace();

            return false;
        }
    }
}
