<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <fmt:setLocale value="${sessionScope.locale}"/>
        <fmt:setBundle basename="messages" var="i18n"/>

        <title><fmt:message bundle="${i18n}" key="${title}"/></title>

        <link href="assets/css/bootstrap.css" rel="stylesheet">
        <link href="assets/css/jsw_style.css" rel="stylesheet">
        <link href="assets/css/font-awesome.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans|Paytone+One|Ropa+Sans" rel="stylesheet">
        <link href="../../../favicon.ico" rel="shortcut icon" type="image/x-icon">
    </head>
    <body>
        <%-- Header --%>
        <%@ include file="../common/header_unlog.jsp" %>

        <%-- Body --%>
        <c:choose>
            <c:when test="${title eq 'main.title'}">
                <%@include file="../main_page/main.jsp" %>
            </c:when>
            <c:when test="${title eq 'login.title'}">
                <%@include file="../login/main.jsp" %>
            </c:when>
            <c:when test="${title eq 'application.title'}">
                <%@include file="../application/application.jsp" %>
            </c:when>
            <c:when test="${title eq 'Result_success'}">
                <%@include file="../compilation_results/result_success.jsp" %>
            </c:when>
            <c:when test="${title eq 'Result_fail'}">
                <%@include file="../compilation_results/result_fail.jsp" %>
            </c:when>
            <c:when test="${title eq 'statistics.title'}">
                <%@include file="../statistics/statistics.jsp" %>
            </c:when>
            <c:when test="${title eq 'rating.title'}">
                <%@include file="../rating/rating.jsp" %>
            </c:when>
            <c:when test="${title eq 'admin.title'}">
                <%@include file="../admin/admin.jsp" %>
            </c:when>
        </c:choose>

        <%-- Footer --%>
        <%@include file="../common/footer.jsp" %>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>

        <script>
            $('.carousel').carousel({
                interval: 3000
            });
        </script>
    </body>
</html>
