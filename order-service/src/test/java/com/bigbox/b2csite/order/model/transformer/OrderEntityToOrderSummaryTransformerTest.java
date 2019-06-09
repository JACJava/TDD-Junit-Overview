package com.bigbox.b2csite.order.model.transformer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.entity.OrderItemEntity;
//import com.bigbox.b2csite.order.model.transformer.OrderEntityToOrderSummaryTransformer;



public class OrderEntityToOrderSummaryTransformerTest {

	private OrderEntityToOrderSummaryTransformer target = null;
	
	@Before
	public void setup() {
		target = new OrderEntityToOrderSummaryTransformer();
	}
	
	@Test
	public void test_transform_success() {
		
		// set the order number equal to a random number
		String orderNumberFixture = UUID.randomUUID().toString();
		
		// create the order with that order number
		OrderEntity orderEntityFixture = new OrderEntity();
		orderEntityFixture.setOrderNumber(orderNumberFixture);
		
		// create the item list
		orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());
		
		// create an order item and set the quantity of the first item to 1, set the selling price to $10.00
		// and add to the list
		OrderItemEntity itemFixture1 = new OrderItemEntity();
		itemFixture1.setQuantity(1);
		itemFixture1.setSellingPrice(new BigDecimal("10.00"));
		orderEntityFixture.getOrderItemList().add(itemFixture1);
		
		// create an order item and set the quantity of the first item to 2, set the selling price to $1,50
		// and add to the list
		OrderItemEntity itemFixture2 = new OrderItemEntity();
		itemFixture2.setQuantity(2);
		itemFixture2.setSellingPrice(new BigDecimal("1.50"));
		orderEntityFixture.getOrderItemList().add(itemFixture2);
		
		OrderSummary result = target.transform(orderEntityFixture);
		
		System.out.println("Order number is:  "+orderEntityFixture.getOrderNumber());
		
		System.out.println("Order quantity is:  "+result.getItemCount());
		
		System.out.println("Order total is:  "+result.getTotalAmount());
		
		// check that the order exists
		Assert.assertNotNull(result);
		
		//  check that the order number is what we set it to (random uuid)
		Assert.assertEquals(orderNumberFixture, result.getOrderNumber());
		
		
		//  check that we have three items in the order
		Assert.assertEquals(3, result.getItemCount());
		
		//  check that our order total is $13.00 ($10.00 + (2*$1.50)) 
		Assert.assertEquals(new BigDecimal("13.00"), result.getTotalAmount());
		
	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_transform_inputIsNull() {
		target.transform(null);
				
	}
	
	@Test
	public void test_transform_noItemsInOrder() {
		
		// set the order number equal to a random number
		String orderNumberFixture = UUID.randomUUID().toString();
		
		// create the order with that order number
		OrderEntity orderEntityFixture = new OrderEntity();
		orderEntityFixture.setOrderNumber(orderNumberFixture);
		
		// create the item list
		orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());
		
		OrderSummary result = target.transform(orderEntityFixture);
		
		Assert.assertNotNull(result);
		
		Assert.assertEquals(0, result.getItemCount());
		Assert.assertEquals(new BigDecimal("0.00"), result.getTotalAmount());
	}

	
	
}
