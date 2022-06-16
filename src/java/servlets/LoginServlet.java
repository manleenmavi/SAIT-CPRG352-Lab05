package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import service.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Current session
        HttpSession currentSession = request.getSession();
        
        // Current user
        User currentUser = (User)currentSession.getAttribute("currentUser");
        if (currentUser != null) {
            if (request.getParameter("logout") != null) {
                request.setAttribute("message", currentUser.getUsername() + " has been successfully logout.");
                currentSession.invalidate();   
            } else {
                response.sendRedirect("home");
                return;
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if( username == null || password == null || username.equals("") || password.equals("")) {
            request.setAttribute("message", "Username/Password field should not be empty.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        AccountService accountService = new AccountService();
        User newUser = accountService.login(username, password);
        if (newUser == null) {
            request.setAttribute("message", "Username and password combination is incorrect.");
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        // Current session
        HttpSession currentSession = request.getSession();
        currentSession.setAttribute("currentUser", newUser);
        
        response.sendRedirect("home");
    }

}
