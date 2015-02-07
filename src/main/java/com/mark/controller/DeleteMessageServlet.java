package com.mark.controller;

import com.mark.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Mark
 * Date  : 2015/2/7
 * Time  : 16:21
 */
@WebServlet(name = "DeleteMessageServlet")
public class DeleteMessageServlet extends HttpServlet {

    private MessageService service = new MessageService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] url = request.getParameter("uuid").split("/");
        service.delete(url[url.length-1]);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
