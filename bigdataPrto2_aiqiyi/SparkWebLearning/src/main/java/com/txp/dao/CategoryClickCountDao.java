package com.txp.dao;

import com.txp.domain.CategoryClickCount;
import com.txp.domain.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CategoryClickCountDao {
    public List<CategoryClickCount> query(String day) throws IOException {
        List<CategoryClickCount> list = new ArrayList<>();

        Map<String, Long> map = HBaseUtils.getInstatnce().query("category_click", day);

        for (Map.Entry<String,Long> entry:map.entrySet()){
            CategoryClickCount categoryClickCount = new CategoryClickCount();
            categoryClickCount.setName(entry.getKey());
            categoryClickCount.setValue(entry.getValue());
            list.add(categoryClickCount);
        }

        return list;
    }

    public static void main(String[] args) throws IOException {
        CategoryClickCountDao dao = new CategoryClickCountDao();
        List<CategoryClickCount> list = dao.query("20181031");
        for (CategoryClickCount c: list){
            System.out.println(c.getName()+" : "+c.getValue());
        }
    }

}
