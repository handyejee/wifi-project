package database;

import board.ResultDto;
import java.sql.*;

public class InsertData {
    public int InsertWifiInfo(ResultDto resultDto) {
        // INSERT 구문
        String sql = "INSERT INTO wifi\n" +
                "    (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2,X_SWIFI_INSTL_FLOOR,X_SWIFI_INSTL_TY,X_SWIFI_INSTL_MBY,X_SWIFI_SVC_SE,X_SWIFI_CMCWR,X_SWIFI_CNSTC_YEAR,X_SWIFI_INOUT_DOOR,X_SWIFI_REMARS3,LAT,LNT,WORK_DTTM\n" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        //2. 커넥션 객체 생성
        ResultSet rs = null;

        int totalAffected = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            // try-with-resources 문법: close 없이 자동으로 닫히게 하는 역할
            // 리소스 해제시 발생하는 오류도 catch 에서 잡을 수 있음

            for (ResultDto.TbPublicWifiInfoDto.RowDto row : resultDto.getTbPublicWifiInfo().getRow()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, row.getX_SWIFI_MGR_NO());
                    statement.setString(2, row.getX_SWIFI_WRDOFC());
                    statement.setString(3, row.getX_SWIFI_MAIN_NM());
                    statement.setString(4, row.getX_SWIFI_ADRES1());
                    statement.setString(5, row.getX_SWIFI_ADRES2());
                    statement.setString(6, row.getX_SWIFI_INSTL_FLOOR());
                    statement.setString(7, row.getX_SWIFI_INSTL_TY());
                    statement.setString(8, row.getX_SWIFI_INSTL_MBY());
                    statement.setString(9, row.getX_SWIFI_SVC_SE());
                    statement.setString(10, row.getX_SWIFI_CMCWR());
                    statement.setString(11, row.getX_SWIFI_CNSTC_YEAR());
                    statement.setString(12, row.getX_SWIFI_INOUT_DOOR());
                    statement.setString(13, row.getX_SWIFI_REMARS3());
                    statement.setBigDecimal(14, row.getLAT());
                    statement.setBigDecimal(15, row.getLNT());
                    statement.setString(16, row.getWORK_DTTM());

                    int rowsAffected = statement.executeUpdate();
                    totalAffected += rowsAffected;

                    if (rowsAffected > 0) {
                        System.out.println(totalAffected);
                    } else {
                        System.out.println("db 입력 실패");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            //6. 객체 연결 해제(close)
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalAffected;
    }
}

