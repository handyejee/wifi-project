<%@ page contentType="text/html;charset=UTF-8" language="java" import="board.APIResult" %>
<%@ page import="board.ResultDto" %>
<%@ page import="database.SelectDistance" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="display-wifi.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>와이파이 정보 구하기</h1>
<div>
    <p><a href="http://localhost:8080/display_wifi.jsp">홈</a> | <a href="http://localhost:8080/historyServlet">위치 히스토리
        목록</a> | <a href="http://localhost:8080/save_wifi.jsp">OPEN API 정보 가져오기</a></p>
</div>
<div>
    <form action="selectDistance" method="GET" id="send-location" name="send-location">
        <label>LAT : <input type="text" name="lat" class="lat" id="lat"></label>
        <label>LNT : <input type="text" name="lnt" class="lnt" id="lnt"></label>
        <button id="getLocation">내 위치 가져오기</button>
        <button type="submit" id="getNear">근처 WIFI 정보 보기</button>
    </form>
</div>
<table class="wifi-info-table">
    <thead>
    <tr class="list-header">
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    </thead>
    <tbody>
    <%
        // API 호출하여 데이터 가져오기
        List<SelectDistance.WIFILocation> locations =  (List<SelectDistance.WIFILocation>) request.getAttribute("locationList");

        if (locations != null && !locations.isEmpty()) {

        for (SelectDistance.WIFILocation location : locations) {
    %>
    <tr>
        <td><%= location.getDistance() %>
        </td>
        <td><%= location.getMgrNo() %>
        </td>
        <td><%= location.getWrdofc() %>
        </td>
        <td><%= location.getMainNm() %>
        </td>
        <td><%= location.getAdres1() %>
        </td>
        <td><%= location.getAdres2() %>
        </td>
        <td><%= location.getInstlFloor() %>
        </td>
        <td><%= location.getInstlTy() %>
        </td>
        <td><%= location.getInstlMby() %>
        </td>
        <td><%= location.getSvcSe() %>
        </td>
        <td><%= location.getCmcwr() %>
        </td>
        <td><%= location.getCnstcYear() %>
        </td>
        <td><%= location.getInoutDoor() %>
        </td>
        <td><%= location.getRemars3() %>
        </td>
        <td><%= location.getLat() %>
        </td>
        <td><%= location.getLnt() %>
        </td>
        <td><%= location.getWorkDttm() %>
        </td>
    </tr>
    <%
            }
        } else {

            out.println("<tr><td class=\"retrieve-msg\" colspan='17'>위치 정보를 입력한 후에 조회해 주세요.</td></tr>");
        }
    %>
    </tbody>
</table>
<script src="getLocation.js"></script>
</body>
</html>
