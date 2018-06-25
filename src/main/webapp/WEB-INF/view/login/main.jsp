<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="content">
            <div class="slogan">
                <h2>Try a new challenge now</h2>
                <h2>Just log in to the app</h2>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <!--Login Form-->
    <form class="form" action="frontController?command=login" method="post">
        <img src="assets/img/male-circle.png">
        <div class="error">
            <p>${requestScope.errorMsg}</p>
        </div>
        <div class="dws-input">
            <input type="text" name="login" placeholder="Enter login" maxlength="30">
        </div>
        <div class="dws-input">
            <input type="password" name="password" placeholder="Enter password" maxlength="20">
        </div>
        <input class="dws-submit" type="submit" name="submit" value="ENTER">
        <br/>
        <a href="registration.jsp">Registration</a>
    </form>
</div>
