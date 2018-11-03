package com.txp.sparkweb;


import com.txp.dao.CategoryClickCountDao;
import com.txp.dao.CategorySearchCountDao;
import com.txp.domain.CategoryClickCount;

import com.txp.domain.CategorySearchCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StatCategoryApp {

    private static Map<String,String> category_video=new HashMap<>();
    static {
        category_video.put("1","偶像爱情");
        category_video.put("2","宫斗权谋");
        category_video.put("3","都市生活");
        category_video.put("4","玄幻史诗");
        category_video.put("5","罪案谍战");
        category_video.put("6","历险科幻");
    }
    private static Map<String,String> source=new HashMap<>();
    static {
        source.put("AiQiYi","直达");
        source.put("SearchEngines","搜索引擎");
    }
    private static Map<String,String> source_Category=new HashMap<>();
    static {
        source_Category.put("AiQiYi","爱奇艺直达");
        source_Category.put("cn.bing.com","百度");
        source_Category.put("search.yahoo.com","搜狗");
        source_Category.put("www.baidu.com","雅虎");
        source_Category.put("www.sogou.com","必应");
    }

    @Autowired
    CategoryClickCountDao dao=new CategoryClickCountDao();
    @Autowired
    CategorySearchCountDao sdao=new CategorySearchCountDao();

    //视频点击的分类
    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ModelAndView echartCat(){
        return new ModelAndView("categoryEchart");
    }

    @RequestMapping(value = "/click",method = RequestMethod.POST)
    public List<CategoryClickCount> query() throws IOException {
       //return new ModelAndView("test");
        List<CategoryClickCount> list = dao.query("20181103");
        for (CategoryClickCount c : list){
            String name = category_video.get(c.getName().substring(9));
            if(name!=null){
                c.setName(name);
            }else {
                c.setName("其他");
            }
        }
        return list;
    }

    //点击视频的来源分类
    @RequestMapping(value = "/categorySearch",method = RequestMethod.GET)
    public ModelAndView echartCatS(){
        return new ModelAndView("categorySearchEchart");
    }

    //点击视频的来源分类  得到的是各个分类的来源    比如 爱奇艺 120   百度 12  搜狗 23  必应 56 这样子的数据
    @RequestMapping(value = "/categoryOfSearch",method = RequestMethod.POST)
    public List<CategorySearchCount> cateSearch() throws IOException {
        List<CategorySearchCount> list = sdao.query("20181103");
        for (CategorySearchCount c : list){
            String source_name=c.getName();
            String name = source_Category.get(source_name.split("_")[1]);// AiQiYi 得到  爱奇艺直达
            if(name!=null){
                c.setName(name);
            }else{
                c.setName("其他");
            }
        }
        return list;
    }

    @RequestMapping(value = "/clickCategory",method = RequestMethod.POST)
    public List<CategorySearchCount> querySearch() throws IOException {
        List<CategorySearchCount> list = sdao.query("20181103");
        long count=0;
        for (CategorySearchCount c : list){
            String source_name=c.getName();
            String splitsName = source_name.split("_")[1];// AiQiYi
            c.setName(source_Category.get(splitsName));///////修改
            if (!splitsName.equals("AiQiYi")){
                count+=c.getValue(); //得到通过搜索引擎访问的次数
            }
        }
        list.add(new CategorySearchCount("搜索引擎",count));
        List<CategorySearchCount> list1=new ArrayList<CategorySearchCount>();
        //只保留  "AiQiYi"   Search_Engines
        for (CategorySearchCount c : list){
            String source_name=c.getName();
            if (source_name.equals("爱奇艺直达")){
                list1.add(c);
            }else if (source_name.equals("搜索引擎")){
                list1.add(c);
            }

        }
        return list1;
    }

    public static void main(String[] args) throws IOException {
//        StatCategoryApp sa=new StatCategoryApp();
//        List<CategorySearchCount> list = sa.cateSearch();
//        for (CategorySearchCount c : list){
//            System.out.println(c.getName()+ " : "+c.getValue());
//        }
        StatCategoryApp sa =new StatCategoryApp();
        List<CategorySearchCount> list = sa.cateSearch();
        //List<CategorySearchCount> list1 = sa.querySearch();

        for (CategorySearchCount c:list){
            System.out.println(c.getName()+" : "+c.getValue());
        }
    }
}
