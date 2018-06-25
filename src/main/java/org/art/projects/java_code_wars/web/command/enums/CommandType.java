package org.art.projects.java_code_wars.web.command.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.art.projects.java_code_wars.web.command.Controller;
import org.art.projects.java_code_wars.web.command.impl.*;

@Getter
@AllArgsConstructor
public enum CommandType {

    MAIN("main.jsp", "Main", "main.title", new MainController()),
    REGISTRATION("registration.jsp", "Registration", "registration.title", new RegistrationController()),
    LOGIN("main.jsp", "Login", "login.title", new LoginController()),
    LOGOUT("main.jsp", "Logout", "logout.title", new LogoutController()),
    APPLICATION("main.jsp", "Application", "application.title", new ApplicationController()),
    STATISTICS("statistics.jsp", "Statistics", "statistics.title", new StatisticsController()),
    RATING("rating.jsp", "Rating", "rating.title", new RatingController()),
    COMPILER("rating.jsp", "Compiler", "compiler.title", new CompilerController()),
    CHECK_LOGIN_AJAX("", "checkLogin", "", new CheckLoginController()),
    ADMIN("admin.jsp", "Admin", "admin.title", new AdminController());

    private String pagePath;
    private String pageName;
    private String i18nKey;
    private Controller controller;

    public static CommandType getByPageName(String page) {
        for (CommandType type : CommandType.values()) {
            if (type.pageName.equalsIgnoreCase(page)) {
                return type;
            }
        }
        //Default page
        return MAIN;
    }
}
