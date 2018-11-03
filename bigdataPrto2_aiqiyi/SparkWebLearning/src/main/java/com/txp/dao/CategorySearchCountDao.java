package com.txp.dao;

import com.txp.domain.CategorySearchCount;
import com.txp.domain.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CategorySearchCountDao {
    public  List<CategorySearchCount> query(String day) throws IOException {
        List<CategorySearchCount> list = new ArrayList<>();

        Map<String, Long> map = HBaseUtils.getInstatnce().query("category_serach_count", day);

        for (Map.Entry<String,Long> entry:map.entrySet()){
            CategorySearchCount category= new CategorySearchCount();
            category.setName(entry.getKey());
            category.setValue(entry.getValue());
            list.add(category);
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        CategorySearchCountDao dao = new CategorySearchCountDao();
        List<CategorySearchCount> list = dao.query("20181102");
        for (CategorySearchCount c: list){
            System.out.println(c.getName()+" : "+c.getValue());
        }

    }
}
