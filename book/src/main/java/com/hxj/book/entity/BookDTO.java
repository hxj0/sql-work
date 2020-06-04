package com.hxj.book.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookDTO implements Serializable {


    private static final long serialVersionUID=1L;

    private Integer id;

    private String name;

    private String author;

    private String cover;

    private String category;

    private Integer count;

    private Double price;

    private String introduction;

}
