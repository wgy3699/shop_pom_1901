package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/20 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {


    @TableId(type = IdType.AUTO)
    private int id;

    private String gname;

    private String ginfo;

    private BigDecimal gprice;

    private String gimages;

    private int tid = 1;

    private int gsave = 0;
}
