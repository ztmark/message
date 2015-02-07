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
 * Date  : 2015/2/6
 * Time  : 19:41
 */
@WebServlet(name = "MessageServlet")
public class AddMessageServlet extends HttpServlet {

    private MessageService service = new MessageService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");
        String duration = request.getParameter("duration");
        Message message = service.insert(text, duration);
        PrintWriter out = response.getWriter();
        if (message == null) {
            request.getSession().setAttribute("error","出问题啦，请重新创建！");
            //request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("msg", message);
            StringBuilder sb = new StringBuilder();
            sb.append("{\"uuid\":\"").append(message.getUuid()).append("\",")
                    .append("\"password\":\"").append(message.getPassword()).append("\",")
                    .append("\"expire\":").append(message.getExpire()).append("}");
            out.write(sb.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
