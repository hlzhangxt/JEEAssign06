/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.Product;
import beans.ProductList;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Administrator
 */
@MessageDriven(mappedName = "jms/Queue")
public class ProductListener implements MessageListener {

    @Inject
    ProductList products;

    @Override
    public void onMessage(Message message) {

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (!(message instanceof TextMessage)) {
            return;
        }
        try {
            String m = ((TextMessage) message).getText();

            JsonReader jsonReader = Json.createReader(new StringReader(m));
            JsonObject json = jsonReader.readObject();
            Product p = new Product();
            p.setName(json.getString("Name"));
            p.setQuantity(json.getInt("Quantity"));
            p.setDescription(json.getString("Description"));
            products.add(p);

        } catch (Exception ex) {
            Logger.getLogger(ProductListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
