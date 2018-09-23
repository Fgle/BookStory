package dao;

import java.util.List;

import domain.Category;

public interface CategoryDao {

    void add(Category category);//添加分类

    Category find(String id);//查找分类

    List<Category> getAll();//查看所有分类

    void update(Category category);//更新分类信息

    void delete(String id);//删除分类


}