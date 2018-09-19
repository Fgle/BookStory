package domain;

import utils.WebUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Cart {
    private String id;
    private Map<String, CartItem> map = new HashMap<String, CartItem>();
    private double price;
    private User user;
//    private Set<CartItem> cartItems = new HashSet<CartItem>();

    public void add(Book book){
        CartItem item = map.get(book.getId());
        if(item ==null){
            item = new CartItem();
            item.setId(WebUtils.makeID());
            item.setBook(book);
            item.setQuantity(1);
            item.setPrice(item.getPrice());
            map.put(book.getId(), item);
        }else{
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void removeOne(Book book) {
        CartItem item = map.get(book.getId());
        if(item == null)
            return;
        if(item.getQuantity() == 1) {
            map.remove(book.getId());
        }
        else {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    public void delete(Book book) {
        CartItem item = map.get(book.getId());
        if(item != null)
            map.remove(book.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {

        this.map = map;
    }

    public double getPrice() {

        double totalprice = 0;
        for(Map.Entry<String, CartItem> me : map.entrySet()){
            CartItem item = me.getValue();
            totalprice = totalprice + item.getPrice();
        }
        this.price = totalprice;
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Set<CartItem> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(Set<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }
}
