package com.sys.recommend.controller;


import com.sys.recommend.entity.Joins;
import com.sys.recommend.service.JoinService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/join")
public class JoinController extends BaseController{

    @Autowired
    private JoinService joinService;


    /**
     * @Author LuoRuiJie
     * @Description 创建加入小组的申请
     * @Date
     * @Param Join
     * @return Resp
     **/
    @PostMapping("/createJoinApplication/{id}")
    public Resp createJoinApplication(@PathVariable int id){
        int CurrentUserId = Integer.parseInt(getSenderId());
        Joins joins = new Joins();
        joins.setUserId(CurrentUserId);
        joins.setGroupId(id);
        if (joinService.save(joins)){
            return Resp.ok("加入小组成功");
        }
        return Resp.err("加入小组失败");
    }

}
