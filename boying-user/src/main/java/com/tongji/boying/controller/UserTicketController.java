package com.tongji.boying.controller;

import com.tongji.boying.common.api.CommonPage;
import com.tongji.boying.common.api.CommonResult;
import com.tongji.boying.model.Ticket;
import com.tongji.boying.service.UserTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台票管理Controller
 */
@Controller
@Api(tags = "TicketController", description = "用户模块票相关API")
@RequestMapping("/ticket")
public class UserTicketController
{
    @Autowired
    private UserTicketService userTicketService;

    @ApiOperation("添加票")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestParam int orderId,
                            @RequestParam int showClassId)
    {
        int count = userTicketService.add(orderId, showClassId);
        if (count > 0)
        {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("显示所有票")
    @RequestMapping(value = "/list/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage<Ticket>> list(@PathVariable int orderId,
                                                 @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                 @RequestParam(required = false, defaultValue = "5") Integer pageSize)
    {
        List<Ticket> tickets = userTicketService.list(orderId, pageNum, pageSize);
        if (tickets.size() == 0) return CommonResult.failed("当前用户无票!");
        return CommonResult.success(CommonPage.restPage(tickets));
    }

    @ApiOperation("获取票详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Ticket> getItem(@PathVariable int id)
    {
        Ticket ticket = userTicketService.getItem(id);
        if (ticket == null) return CommonResult.failed("当前用户无此票!");
        return CommonResult.success(ticket);
    }
}
