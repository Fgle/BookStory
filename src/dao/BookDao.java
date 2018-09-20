package dao;

import java.util.List;

import domain.Book;

public interface BookDao {

    void add(Book book);

    Book findByID(String id);

    Book findByName(String name);

    void update(Book book);//通过bookID更新图书信息

    public List<Book> getPageData(int startindex, int pagesize);

    public int getTotalRecord();

    public List<Book> getPageData(int startindex, int pagesize, String category_id);

    public int getTotalRecord(String category_id);

    public void delete(Book book);

    public void deleteById(String id);

}
