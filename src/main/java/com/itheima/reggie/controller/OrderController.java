package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单信息：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

//    @GetMapping("/page")
//    public R<Page> page(int page, int pageSize, String name) {
////        获取数据库里符合当页需要的信息
//        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);
//        Page pageInfo = new Page(page, pageSize);
//        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
//        queryWrapper.orderByDesc(Employee::getUpdateTime);
//
//        employeeService.page(pageInfo,queryWrapper);
//        return R.success(pageInfo);
//    }
}
