package com.qf.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/22 19:49
 */
@Data
public class Page<T> implements Serializable {

    //每页显示的条数
    private int pageSize = 5;

    //总条数
    private int totalPage;

    //当前页
    private int currentPage = 1;

    //总条数
    private int totalCount;

    //路径
    private String url;

    List<T> list;
}
