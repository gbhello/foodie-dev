package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gengbin
 * @date 2021/2/11
 */
@Component
public class OrderJob {
    @Autowired
    private OrderService orderService;

    // @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder() {
        // orderService.closeOrder();
        System.out.println("执行定时任务：当前时间为" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}