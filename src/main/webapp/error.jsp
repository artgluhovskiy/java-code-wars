<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    </head>
    <body>

        <%-- Header --%>
        <%@include file="WEB-INF/view/common/header_unlog.jsp"%>

        <%-- Body --%>
        <div class="container error_container">
            <form class="error_form" action="frontController?command=${sessionScope.prevPage}" method="post">
                <i class="fa fa-exclamation-triangle fa-5x" aria-hidden="true"></i>
                <div class="error_block">
                    <h2>${requestScope.errorMsg}</h2>
                    <h3>Drink a cup of coffee now <i class="fa fa-coffee fa-2x" aria-hidden="true"></i><br> and try later!</h3>
                </div>
                <br>
                <input class="dws-submit" type="submit" name="submit" value="RETURN">
            </form>
        </div>

        <%-- Footer --%>
        <%@include file="WEB-INF/view/common/footer.jsp"%>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>

