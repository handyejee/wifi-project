<%@ page import="database.InsertData" %>
<%@ page import="board.ResultDto" %>
<%@ page import="board.APIResult" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="save-wifi.css" rel="stylesheet" type="text/css">
</head>
<body>

    <%

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 드라이버 명시적으로 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Logger 선언
        Logger logger = Logger.getLogger("MyLogger");

        ResultDto resultDto = APIResult.printResult(); // API 결과값 가져오기
        logger.info("API Result: " + resultDto);
        InsertData insertData = new InsertData(); // INSERT문 클래스 호출
        int countWIFI = 0;
        try {
            countWIFI = insertData.InsertWifiInfo(resultDto); // INSERT 문 수행
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
    <div class="wifi-info">
        <h1><%= countWIFI %> 개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
        <a href="http://localhost:8080/display_wifi.jsp">홈 으로 가기</a>
    </div>

</body>
</html>
