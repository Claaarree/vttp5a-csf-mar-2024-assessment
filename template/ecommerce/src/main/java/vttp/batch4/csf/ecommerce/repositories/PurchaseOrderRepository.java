package vttp.batch4.csf.ecommerce.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;

import static vttp.batch4.csf.ecommerce.Utils.INSERT_INTO_LINEITEMS;
import static vttp.batch4.csf.ecommerce.Utils.INSERT_INTO_ORDERS;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // You may only add Exception to the method's signature
  @Transactional
  public void create(Order order) throws DataAccessException{
    // TODO Task 3
    template.update(INSERT_INTO_ORDERS, 
        order.getOrderId(),
        order.getDate(),
        order.getName(),
        order.getAddress(),
        order.getPriority(),
        order.getComments());

    List<LineItem> items = order.getCart().getLineItems();
    List<Object[]> params = items.stream()
        .map(i -> new Object[]{
              order.getOrderId(), 
              i.getProductId(), 
              i.getQuantity(), 
              i.getName(), 
              i.getPrice()})
        .collect(Collectors.toList());

    template.batchUpdate(INSERT_INTO_LINEITEMS, params);
  }
}
