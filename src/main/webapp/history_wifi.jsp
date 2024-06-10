<%@ page contentType="text/html;charset=UTF-8" language="java" import="board.APIResult" %>
<%@ page import="java.util.*" %>
<%@ page import="database.HistoryDAO" %>

<html>
<head>
    <title>위치 히스토리 목록</title>
    <link href="display-wifi.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>위치 히스토리 목록</h1>
<div>
    <p><a href="http://localhost:8080/display_wifi.jsp">홈</a> | <a href="http://localhost:8080/historyServlet">위치 히스토리
        목록</a> | <a href="http://localhost:8080/save_wifi.jsp">OPEN API 정보 가져오기</a></p>
</div>
<table class="wifi-info-table">
    <thead>
    <tr class="list-header">
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        // DB 저장 된 값 호출해오기
        List<String[]> historyList = (List<String[]>) request.getAttribute("historyList");

        if (historyList != null && !historyList.isEmpty()) {
            for (String[] row : historyList) {
    %>
    <tr>
        <form action="historyServlet" method="POST">
            <td>
                <input type="hidden" name="id" value="<%= row[0] %>"><%= row[0] %>
            </td>
            <td>
                <%= row[1] %>
            </td>
            <td>
                <%= row[2] %>
            </td>
            <td>
                <%= row[3] %>
            </td>
            <td class="delete-btn">
                <button type="submit" id="delete-history">삭제</button>
            </td>
        </form>
    </tr>
    <%
            }
        } else {
            out.println("<tr><td class=\"retrieve-msg\" colspan='5'>위치 정보 조회 이후 확인 할 수 있습니다.</td></tr>");
        }
    %>
    </tbody>
</table>
<script src="getLocation.js"></script>
</body>
</html>

