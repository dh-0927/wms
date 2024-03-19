package com.dh.wms.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticGoodsVo {

    private int quantity;

    private double totalPrice;
}
