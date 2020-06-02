package com.hxj.book.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Log implements Serializable {
    private Integer bookId;
    private Integer userId;
    private String bookname;
    private String username;
    private Date borrowDate;
    private Date returnDate;
}
