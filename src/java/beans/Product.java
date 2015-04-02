/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Administrator
 */
public class Product {
    private int ProductID;
    private String Name;
    private String Description;
    private int Quantity;

    public Product(int ProductID, String Name, String Description, int Quantity) {
        this.ProductID = ProductID;
        this.Name = Name;
        this.Description = Description;
        this.Quantity = Quantity;
    }

    public Product() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    
    
    public JsonObject toJSON() {
      
            JsonObjectBuilder jBuilder = Json.createObjectBuilder();
            jBuilder.add("ProductID", ProductID);
            jBuilder.add("Name", Name);
            jBuilder.add("Quantity", Quantity);
            jBuilder.add("Description", Description);
            return jBuilder.build();

    }
    
    
}
