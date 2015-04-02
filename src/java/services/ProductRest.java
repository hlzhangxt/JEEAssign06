/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import beans.Product;
import beans.ProductList;
import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
/**
 *
 * @author Administrator
 */
@Path("products")
@RequestScoped
public class ProductRest {

   @Inject 
   ProductList products;
    
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response doGet(@PathParam("id") Integer id)
    {
        Product p = products.get(id);
        if (p != null)
           return Response.ok(p.toJSON()).build();
        else
            return Response.status(404).build();
      // return Response.ok("OK").build();
      
        
    }
    
    @GET
    @Produces({"application/json"})
    public Response doGetAll()
    {
     
        
        
       return Response.ok(products.toJSON()).build();
      
        
    }
    
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createWithReturn(Product entity) {
      
        Product p = products.add(entity);
        if (p != null)
        {
            URI uri = URI.create("/" + p.getProductID());
           return Response.temporaryRedirect(uri).build();
        }
        else
            return Response.status(500).build();
        
    }
    
    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response editWithReturn(@PathParam("id") Integer id, Product entity) {
    //   if (super.find(id) == null)  Response.status(500).build();
      Product p = products.set(id, entity);
      
       if (p != null)
        {
            URI uri = URI.create("/" + p.getProductID());
           return Response.temporaryRedirect(uri).build();
        }
        else
            return Response.status(500).build();
      
        
    }
    
    
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
       // if (super.find(id) == null)  Response.status(500).build();
        
        if (products.remove(id))
            return Response.ok("").build();
        else
          return Response.status(500).build();  
    }  
    
    
}
