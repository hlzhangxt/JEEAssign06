/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Administrator
 */
@ApplicationScoped
public class ProductList {

    private HashMap<Integer, Product> productList = new HashMap<Integer, Product>();

    private Connection connection = null;

    public HashMap<Integer, Product> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Integer, Product> productList) {
        this.productList = productList;
    }

    public ProductList() {

       
        connection = getConnection();

        try {

           
            String query = "SELECT * FROM product";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Product p = new Product(rs.getInt("ProductID"), rs.getString("Name"), rs.getString("Description"), rs.getInt("Quantity"));
                
                productList.put(p.getProductID(), p);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void finalize() throws Throwable {

        if (connection != null) {
            try {
                connection.close();
                connection = null;

            } catch (SQLException ex) {
                Logger.getLogger(ProductList.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception! " + e.getMessage());
        }

        String url = "jdbc:mysql://localhost:3306/cpd4414assign3";
        try {
            connection = (Connection) DriverManager.getConnection(url,
                    "usertest", "123456");
        } catch (SQLException e) {
            System.out.println("Failed to Connect! " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

   
    public void remove(Product product) {
        if (productList.containsKey(product.getProductID())) {

            try {
               
                int productid = product.getProductID();

                String query = "delete from product  WHERE ProductID = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                System.out.println(query);

                pstmt.setInt(1, productid);

                int updates = pstmt.executeUpdate();
                
                if (updates > 0)
                    productList.remove(product.getProductID());

            } catch (Exception ex) {
                Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }

       
            

        }

    }

    public boolean remove(int id) {
        if (productList.containsKey(id)) {

            try {
                //  super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.

                String query = "delete from product  WHERE ProductID = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                System.out.println(query);

                pstmt.setInt(1, id);

                int updates = pstmt.executeUpdate();
                if (updates > 0)
                {
                    productList.remove(id);
                    return true;
                    
                }else
                    return false;

            } catch (Exception ex) {
                Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return false;
            }

           
            

        }
        return false;

    }

    public Product add(Product p) {

        try {
            //  super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.

            String query = "insert into product(Name, Description, Quantity) values(?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(query);

            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getDescription());
            pstmt.setInt(3, p.getQuantity());

            int updates = pstmt.executeUpdate();

            if (updates > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int productid = rs.getInt(1);
                    productList.put(productid, p);

                }
                return p;
            }else
                return null;

        } catch (Exception ex) {
            Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }

    }

    public Product set(int id, Product product) {
        if (productList.containsKey(id)) {

            try {
                //  super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.

                int productid = id;

                String query = "update product set Name=?, Description=?, Quantity=?   WHERE ProductID = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                System.out.println(query);

                pstmt.setString(1, product.getName());
                pstmt.setString(2, product.getDescription());
                pstmt.setInt(3, product.getQuantity());
                pstmt.setInt(4, id);

                int updates = pstmt.executeUpdate();
                
                if (updates > 0)
                {
                    productList.put(id, product);
                    
                    return product;
                }else return null;

            } catch (Exception ex) {
                Logger.getLogger(ProductList.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return null;
            }

            

        }
        return null;
    }

    public Product get(int id) {
        return productList.get(id);
    }

    public JsonArray toJSON() {
        JsonArrayBuilder jsonArrayBuilder =  Json.createArrayBuilder();
      /*  Iterator iter = productList.entrySet().iterator();
        while (iter.hasNext()) {
　　           (Map.Entry) entry = (Map.Entry) iter.next();
　　           Object key = entry.getKey();
　　           Object val = entry.getValue();
　　}*/
        
       /* 
        for (Product p : productList.values()) {
         /*   JsonObjectBuilder jBuilder = Json.createObjectBuilder();
            jBuilder.add("ProductID", p.getProductID());
            jBuilder.add("Name", p.getName());
            jBuilder.add("Quantity", p.getQuantity());
            jBuilder.add("Description", p.getDescription());
            jsonArray.add(p.toJSON());

        }*/
       
       Set set = productList.keySet();
        for(Iterator iterator = set.iterator();iterator.hasNext();)
        {
            int id = (Integer)iterator.next();
            Product p = (Product)productList.get(id);
            jsonArrayBuilder.add(p.toJSON());
        }

        return jsonArrayBuilder.build();

    }

}
