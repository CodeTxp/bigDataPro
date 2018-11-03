package com.txp.applogs.visualize.web.controller;

import com.txp.applogs.visualize.domain.StatBean;
import com.txp.applogs.visualize.service.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController {

    @Resource(name="statService")
    private StatService ss ;
    /**
     * appid = "sdk34734"
     * 本周每天新增用户数
     */
    @RequestMapping("/newusers")
    public String newUsers(){
        StatBean newUsers = ss.findNewUsers();
        System.out.println(newUsers.getCount());
        return "index" ;
    }
    @RequestMapping("/index")
    public String toStatPage(){
        return "index" ;
    }

    /**
     * 测试统计
     */
    @RequestMapping("/stat1")
    @ResponseBody
    public StatBean stat1(){
        StatBean b1 = new StatBean();
        b1.setDate("2017/06/30");
        b1.setCount(1000);
        return b1 ;
    }
    /**
     * 测试统计
     */
    @RequestMapping("/stat2")
    @ResponseBody
    public List<StatBean> stat2(){
        List<StatBean> list = new ArrayList<StatBean>();
        for(int i = 0 ; i < 10 ; i ++){
            StatBean b1 = new StatBean();
            b1.setDate("2017/06/" + (10 + i));
            b1.setCount(100 + i);
            list.add(b1) ;
        }
        return list ;
    }
    /**
     * 测试统计
     */
    @RequestMapping("/stat3")
    @ResponseBody
    public List<StatBean> stat4(){
        List<StatBean> list = new ArrayList<StatBean>();
        list = ss.findWeeksActivers("sdk34734");
        return list;
    }

    //每周新增用户的统计
    @RequestMapping("/weekNewUsers")
    @ResponseBody
    public Map<String, Object> newUsersInWeek() {
        List<StatBean> list = ss.findThisWeekNewUsers("sdk34734");
        Map<String,Object> map = new HashMap<String,Object>();

        String[] xlabels = new String[list.size()] ;
        long[] newUsers = new long[list.size()];
        for(int i = 0 ; i < list.size() ; i ++){
            xlabels[i] = list.get(i).getDate();
            newUsers[i] = list.get(i).getCount();
        }
        map.put("labels",xlabels);
        map.put("data", newUsers);
        for(String s:map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
        return map ;
    }
    //当前3周的每周的活跃用户数目的统计
    @RequestMapping("/weeksActivers")
    @ResponseBody
     public Map<String, Object> newActiversInWeek(){
        List<StatBean> list =ss.findWeeksActivers("sdk34734");
        Map<String,Object> map = new HashMap<String,Object>();
        String[] xlabels = new String[list.size()] ;
        long[] newUsers = new long[list.size()];
        for(int i = 0 ; i < list.size() ; i ++){
            xlabels[i] = list.get(i).getDate();
            newUsers[i] = list.get(i).getCount();
        }
        map.put("labels",xlabels);
        map.put("data", newUsers);
        for(String s:map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
        return map ;
     }
    //当前3周的每周的活跃用户数目的统计
    @RequestMapping("/daySilenters")
    @ResponseBody
     public Map<String, Object> silentersInDay(){
        List<StatBean> list =ss.findDaySilenters("sdk34734");
        Map<String,Object> map = new HashMap<String,Object>();
        String[] xlabels = new String[list.size()] ;
        long[] newUsers = new long[list.size()];
        for(int i = 0 ; i < list.size() ; i ++){
            xlabels[i] = list.get(i).getDate();
            newUsers[i] = list.get(i).getCount();
        }
        map.put("labels",xlabels);
        map.put("data", newUsers);
        for(String s:map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
        return map ;
     }
    //当前3周的每周的活跃用户数目的统计
    @RequestMapping("/dayStartNum")
    @ResponseBody
     public Map<String, Object> StartNumInDay(){
        List<StatBean> list =ss.findDaySilenters("sdk34734");
        Map<String,Object> map = new HashMap<String,Object>();
        String[] xlabels = new String[list.size()] ;
        long[] newUsers = new long[list.size()];
        for(int i = 0 ; i < list.size() ; i ++){
            xlabels[i] = list.get(i).getDate();
            newUsers[i] = list.get(i).getCount();
        }
        map.put("labels",xlabels);
        map.put("data", newUsers);
        for(String s:map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
        return map ;
     }
}
