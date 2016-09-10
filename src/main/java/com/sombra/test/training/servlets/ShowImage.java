package com.sombra.test.training.servlets;

import com.sombra.test.training.objects.LibraryFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "ShowImage", urlPatterns = {"/ShowImage"})
public class ShowImage extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        try(OutputStream out = response.getOutputStream();) {
            int index = Integer.valueOf(request.getParameter("index"));
            LibraryFacade libraryFacade = (LibraryFacade) getServletContext().getAttribute("libraryFacade");
            byte[] image = libraryFacade.getBooks().get(index).getImage();
            response.setContentLength(image.length);
            out.write(image);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
