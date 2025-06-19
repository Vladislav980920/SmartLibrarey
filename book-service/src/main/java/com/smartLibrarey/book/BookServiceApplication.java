package com.smartLibrarey.book;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class BookServiceApplication {
    public static final String BASE_URI = "http://localhost:8081/api/";

    public static HttpServer startServer() {
        final ResourceConfig config = new ResourceConfig()
                .packages("com.smartlibrary.book.controller");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Book service started at " + BASE_URI);

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
    }
}
