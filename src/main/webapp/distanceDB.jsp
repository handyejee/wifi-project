<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="test.Point" %>
<%@ page import="database.SelectDistance" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.ArrayList" %>
<%
    // send_form 파일에서 lat, lnt 값 받아오기
    String lat = request.getParameter("lat");
    String lnt = request.getParameter("lnt");

    // 디버그 출력을 통해 파라미터 값 확인
    out.println("메인화면 경도: " + lat);
    out.println("메인화면 위도: " + lnt);

    // String 으로 받아온 lat, lnt -> double 형태로 바꾸기
    if (lat != null && lnt != null) { // null 값 자바스크립트에서 막아주기 ??
//        double myX = (lat != null) ? Double.parseDouble(lat) : 37.5619240000; // 테스트용 하드코딩
//        double myY = (lnt != null) ? Double.parseDouble(lnt) : 126.9850220000; // 테스트용 하드코딩
        double myX = Double.parseDouble(lat);
        double myY = Double.parseDouble(lnt);

        // 변환된 myX와 myY 값을 사용하여 클라이언트의 위치를 나타내는 Point 객체를 생성
        // 클라이언트에서 받아온 lat와 lnt 값을 사용하여 Point 객체를 생성
        Point myPoint = new Point(myX, myY);

        // 최소 거리를 구하기 위해 MAX_VALUE 선언
        Double minDistance = Double.MAX_VALUE;

        // DB에서 조회한 값 가져오기
        SelectDistance sd = new SelectDistance();
        ArrayList<BigDecimal[]> distances = sd.selectDistance();

        // ArrayList 로 받아온 lat, lnt 값 크기 만큼 반복
        for (BigDecimal[] pair : distances) {
            double latFromDB = pair[0].doubleValue(); // BigDecimal 로 저장한 lat, lnt 값 double로 변환
            double lntFromDB = pair[1].doubleValue(); // BigDecimal 로 저장한 lat, lnt 값 double로 변환
//            out.println("Latitude: " + pair[0] + ", Longitude: " + pair[1]);

            // DB에서 가져온 lat, lnt Point 객체 생성
            Point dbPoint = new Point(latFromDB, lntFromDB);

            // myPoint 객체에서 getDistance 메서드 호출
            double distanceFromCur = myPoint.getDistance(dbPoint);

            // 최단 거리 계산
            if (distanceFromCur < minDistance) {
                minDistance = distanceFromCur;
            }
            System.out.println(distanceFromCur);
            out.println("\n거리 " + distanceFromCur);
        }


    } else {
        out.print("위도 경도 값을 입력해주세요.");
    }


%>