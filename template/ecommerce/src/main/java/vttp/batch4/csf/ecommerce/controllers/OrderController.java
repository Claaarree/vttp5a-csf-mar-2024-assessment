package vttp.batch4.csf.ecommerce.controllers;


import java.io.StringReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@Controller
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  @PostMapping(path = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postOrder(@RequestBody String order) {

    // TODO Task 3
    System.out.println(order);
    JsonObject jObject = Json.createReader(new StringReader(order)).readObject();
    Order o = new Order();
    o.setName(jObject.getString("name"));
    o.setAddress(jObject.getString("address"));
    o.setPriority(jObject.getBoolean("priority"));
    o.setComments(jObject.getString("comments"));

    Cart cart = o.getCart();
    JsonObject cartObj = jObject.getJsonObject("cart");
    JsonArray lineItems = cartObj.getJsonArray("lineItems");
    lineItems.forEach(val -> {
      JsonObject obj = val.asJsonObject();
      LineItem li = new LineItem();
      li.setProductId(obj.getString("prodId"));
      li.setQuantity(obj.getInt("quantity"));
      li.setName(obj.getString("name"));
      li.setPrice((float)obj.getJsonNumber("price").doubleValue());
      cart.addLineItem(li);
    });

    o.setCart(cart);

    try {
      poSvc.createNewPurchaseOrder(o);
      JsonObject success = Json.createObjectBuilder()
          .add("orderId", o.getOrderId())
          .build();
      return ResponseEntity.ok(success.toString());
    } catch (Exception e) {
      // TODO: handle exception
      JsonObject failure = Json.createObjectBuilder()
          .add("message", e.getMessage())
          .build();

      return ResponseEntity.badRequest().body(failure.toString());
    }
  }

  //   {"name":"tedt","address":"123 beach road","priority":false,"comments":"","cart":{"lineItems":[{"prodId":"67da512105239501683d002e","quantity":1,"name":"Masters Blend Tea- Rich 
// Taste","price":1500},{"prodId":"67da512105239501683d002e","quantity":1,"name":"Masters Blend Tea- Rich Taste","price":1500},{"prodId":"67da512105239501683d0641","quantity":2,"name":"Glow Assorted Loose Leaf Tea","price":1995}]}}
}
