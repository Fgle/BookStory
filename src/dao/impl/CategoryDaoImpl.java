package dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.JdbcUtils;
import dao.CategoryDao;
import domain.Category;

public class CategoryDaoImpl implements CategoryDao {

    /* (non-Javadoc)
     * @see dao.impl.CategoryDao#add(domain.Category)
     */
    @Override
    public void add(Category category){
        try{
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "insert into category(id,name,description) values(?,?,?)";
            Object params[] = {category.getId(), category.getName(), category.getDescription()};
            runner.update(sql, params);
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /* (non-Javadoc)
     * @see dao.impl.CategoryDao#find(java.lang.String)
     */
    @Override
    public Category find(String id){
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "select * from category where id=?";
            return (Category)runner.query(sql, new BeanHandler(Category.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /* (non-Javadoc)
     * @see dao.impl.CategoryDao#getAll()
     */
    @Override
    public List<Category> getAll(){
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "select * from category";
            return (List<Category>)runner.query(sql, new BeanListHandler(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "update category set name=?,description=? where id=?";
            Object params[] = { category.getName(), category.getDescription(), category.getId()};
            runner.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "select id from category where name=?";
            String unclassifiedID = (String) runner.query(sql, new BeanHandler(String.class), "未分类");
            sql = "update book set category_id=? where category_id=?";
            Object params[] = {unclassifiedID, id};
            runner.update(sql, params);
            sql = "delete from category where id=?";
            runner.update(sql, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
