package com.mark.controller;

import com.mark.domain.Message;
import com.mark.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Mark
 * Date  : 2015/2/7
 * Time  : 18:26
 */
@WebServlet(name = "ShowMessageServlet")
public class ShowMessageServlet extends HttpServlet {

    private MessageService service = new MessageService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        Message message = service.select(id, password);
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            sb.append("{\"uuid\":\"").append(message.getUuid())
                    .append("\",\"txt\":\"").append(message.getText())
                    .append("\",\"expire\":").append(message.getExpire())
                    .append("}");
            out.write(sb.toString());
        } else {
            out.write("{}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("show.jsp").forward(request, response);
    }
}
