package com.sombra.test.training.servlets;


import com.sombra.test.training.dao.impl.BookDAOImpl;
import com.sombra.test.training.dao.interfaces.BookDAO;
import com.sombra.test.training.entities.Book;
import com.sombra.test.training.objects.LibraryFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteBook", urlPatterns = {"/DeleteBook"})
public class DeleteBook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("selectedBook"));
        LibraryFacade libraryFacade = (LibraryFacade) getServletContext().getAttribute("libraryFacade");
        libraryFacade.deleteBook(id);
        response.sendRedirect(request.getContextPath());
    }
}
