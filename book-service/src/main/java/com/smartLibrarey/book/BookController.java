package com.smartLibrarey.book;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {
    private final BookService bookService = new BookService();

    @GET
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") Long id) {
        return bookService.findById(id);
    }

    @POST
    public Response createBook(Book book) {
        Book created = bookService.save(book);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        return bookService.update(id, book);
    }

    @DELETE
    @Path("/{id}")
    public void deleteBook(@PathParam("id") Long id) {
        bookService.delete(id);
    }
}
