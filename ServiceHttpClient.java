package com.smartlibrary.loan.http;

import com.google.gson.Gson;
import com.smartlibrary.book.model.Book;
import com.smartlibrary.user.model.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

public class ServiceHttpClient {
    private static final String BOOK_SERVICE_URL = "http://localhost:8081/api/";
    private static final String USER_SERVICE_URL = "http://localhost:8082/api/";

    private final Client client = ClientBuilder.newClient();
    private final Gson gson = new Gson();

    public Book getBookById(Long id) {
        String response = client.target(BOOK_SERVICE_URL)
                .path("books/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        return gson.fromJson(response, Book.class);
    }

    public void updateBookAvailability(Long id, Boolean available) {
        client.target(BOOK_SERVICE_URL)
                .path("books/" + id + "/availability")
                .queryParam("available", available)
                .request(MediaType.APPLICATION_JSON)
                .put(null);
    }

    public User getUserById(Long id) {
        String response = client.target(USER_SERVICE_URL)
                .path("users/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        return gson.fromJson(response, User.class);
    }
}