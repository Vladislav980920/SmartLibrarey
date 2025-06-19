package com.smartLibrarey.book;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private Boolean available = true;
}
