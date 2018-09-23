package dao;

import java.util.List;

import domain.Book;

public interface BookDao {

    void add(Book book);//添加图书

    /***查找图书***/
    Book findByID(String id);
    Book findByName(String name);

    void update(Book book);//通过bookID更新图书信息
    /***删除图书***/
    void delete(Book book);
    void deleteById(String id);

    List<Book> getPageData(int startindex, int pagesize);//查询某页图书

    int getTotalRecord();//查询图书总数量

    List<Book> getPageData(int startindex, int pagesize, String category_id);//查询某分类下某页图书

    int getTotalRecord(String category_id);//查询某类图书数量

}
