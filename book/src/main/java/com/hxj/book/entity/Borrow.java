package com.hxj.book.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Borrow对象", description="")
@TableName("borrow")
public class Borrow implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "user_id")
    private Integer userId;

    @TableId(value = "book_id")
    private Integer bookId;

    private Date borrowDate;

    private Date returnDate;

}
