<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Registration form</title>

        <link href="assets/css/bootstrap.css" rel="stylesheet">
        <link href="assets/css/jsw_style.css" rel="stylesheet">
        <link href="assets/css/font-awesome.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans|Paytone+One|Ropa+Sans" rel="stylesheet">
        <link rel="shortcut icon" href="/favicon.ico">

        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
    </head>
    <body>
        <%-- Header --%>
        <%@include file="WEB-INF/view/common/header_unlog.jsp"%>

        <div class="container">
            <div class="row" style="text-align: center;">
                <div class="content">
                    <div class="slogan">
                        <h1>Fill in the registration form</h1>
                    </div>
                </div>
            </div>
        </div>

        <!--Registration Form-->
        <div class="container">
            <form class="registration regform" action="${pageContext.request.contextPath}/frontController?command=registration" method="post">
                <img src="assets/img/male-circle.png">
                <div class="place_error">
                    <c:if test="${not empty requestScope.errorMsg}">
                        <div class="error"><p>${errorMsg}</p></div>
                    </c:if>
                </div>
                <div class="dws-input">
                    <input type="text" name="login" placeholder="Enter login" maxlength="30" class="input_login">
                </div>
                <div class="dws-input">
                    <input type="text" name="fname" placeholder="First name" maxlength="20" class="input_fname">
                </div>
                <div class="dws-input">
                    <input type="text" name="lname" placeholder="Last name" maxlength="20" class="input_lname">
                </div>
                <div class="dws-input">
                    <input type="text" name="birth" placeholder="Birth date: DD-MM-YYYY" class="input_date">
                </div>
                <div class="dws-input">
                    <input type="text" name="email" placeholder="Your email" maxlength="30" class="input_email">
                </div>
                <div class="dws-input">
                    <input type="text" name="clan_name" placeholder="Clan name" maxlength="20" class="input_clan_name">
                </div>
                <div class="dws-input">
                    <input type="password" name="password" placeholder="Enter password" maxlength="20" class="input_password">
                </div>
                <input class="dws-submit" type="submit" name="submit" value="ENTER">
            </form>
        </div>

        <%-- Footer --%>
        <%@include file="WEB-INF/view/common/footer.jsp"%>

        <script src="assets/js/utils.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="assets/js/jquery-3.2.1.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
