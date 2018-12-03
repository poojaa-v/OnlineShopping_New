package io.online.salesorder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.online.salesorder.domain.Customer;
import io.online.salesorder.domain.Item;
import io.online.salesorder.domain.SalesOrderDetails;
import io.online.salesorder.entity.CustomerSOS;
import io.online.salesorder.entity.OrderLineItem;
import io.online.salesorder.entity.SalesOrder;
import io.online.salesorder.repository.CustomerSOSRepository;
import io.online.salesorder.repository.OrderLineItemRepository;
import io.online.salesorder.repository.SalesOrderRepository;

@RestController
public class SalesOrderController {

	
	@Autowired
	SalesOrderRepository salesOrderRepository;

	@Autowired
	OrderLineItemRepository orderLineItemRepository;
	
	@Autowired
	CustomerSOSRepository customerSOSRepository;
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@PostMapping("/orders")
	@HystrixCommand(fallbackMethod="custItemFallback") 	 
	public String insertSalesOrderDetails(@RequestBody SalesOrderDetails salesOrderDetails) throws Exception {
		System.out.println("Inside Controller getOrderDesc >>>>" + salesOrderDetails.getOrderDesc());

		//Validate customer by verifying the table “customer_sos” with cust_id --- Starts
		boolean custBool = false;
		List<CustomerSOS> customerSOSList = customerSOSRepository.findAll();
		if (customerSOSList != null && customerSOSList.size() > 0){
			for (int ii=0 ; ii < customerSOSList.size() ; ii++) {
				System.out.println("customerSOSList.get(ii).getCustId()" + customerSOSList.get(ii).getCustId());
				System.out.println("salesOrderDetails.getCustId()" + salesOrderDetails.getCustId());
				if (customerSOSList.get(ii).getCustId() != null && salesOrderDetails.getCustId() != null){
					if (customerSOSList.get(ii).getCustId().equals(salesOrderDetails.getCustId())) {
						custBool = true;
					} 
				} else {
					//Customer not available -- Exception to be thrown
					System.out.println("<<<<<<<<<Customer not available 2222>>>>");
					salesOrderDetails.setErrVar("custErr");
					throw new Exception();
				}
			}
			
			if(!custBool) {
				//Customer not available -- Exception to be thrown
				System.out.println("<<<<<<<<<Customer not available 1111>>>>");
				salesOrderDetails.setErrVar("custErr");
				throw new Exception();
					
			}
		} else {
			System.out.println("<<<<<<<<<Customer not available 3333>>>>");
			salesOrderDetails.setErrVar("custErr");
			throw new Exception();
		}
			

		//Validate customer by verifying the table “customer_sos” with cust_id --- Ends
		
		// REST call to validate items by calling item service with item name -- itemByName ---Starts 
		System.out.println("Before REST CALL >>>>");		
		RestTemplate restTemplate = new RestTemplate();
		//String fetchItemUrl = "http://localhost:8082/items";
		ResponseEntity<Item> response = null;

		String itemName = "";
		double totalPrice = 0.0;
		Long orderId = 0L;
		List <Item> fetchedItemList = null;
		
		if (salesOrderDetails.getItemNameList() != null && salesOrderDetails.getItemNameList().size() >0 ) {
			fetchedItemList = new ArrayList<Item>();
			Item item = null;
			for (int i=0 ; i < salesOrderDetails.getItemNameList().size() ; i++) {
				item = new Item();
				itemName = salesOrderDetails.getItemNameList().get(i).getItemName();
				item.setItemName(itemName);
				response = restTemplate.getForEntity(fetchItemServiceUrl() + "/items/"+itemName, Item.class);
				System.out.println("After REST CALL >>>>"+response.getBody());
				itemName = response.getBody().getItemName();
				totalPrice = totalPrice + response.getBody().getItemPrice();
				fetchedItemList.add(item);
				System.out.println("itemName>>>>" + itemName + "<<<<<");
				if(itemName.equalsIgnoreCase(null) || itemName.equals("") || itemName.isEmpty()){
					//Item details not available -- Exception to be thrown
					System.out.println("<<<<<<<<<Item details not available 1111111 >>>");
					salesOrderDetails.setErrVar("itemErr");
				}
			}
		} else {
			//Item details not available -- Exception to be thrown
			System.out.println("<<<<<<<<<Item not available >>>>");
			salesOrderDetails.setErrVar("itemErr");
		}

		// REST call to validate items by calling item service with item name -- itemByName --- Ends

		// create order by inserting the order details in sales_order table --- Starts
		if(salesOrderDetails != null ) {

			SalesOrder salesOrder = new SalesOrder();
			if (salesOrderDetails.getOrderDate() != null ) {
				salesOrder.setOrderDate(salesOrderDetails.getOrderDate());
			}
			if (salesOrderDetails.getCustId() != null ) {
				salesOrder.setCustId(salesOrderDetails.getCustId() );
			}
			if (salesOrderDetails.getOrderDesc() != null ) {
				salesOrder.setOrderDesc(salesOrderDetails.getOrderDesc() );
			}
			if (totalPrice != 0.0 ) {
				salesOrder.setTotalPrice(totalPrice);
			}

			salesOrder = salesOrderRepository.save(salesOrder);
			orderId = salesOrder.getOrderId();
			// create order by inserting the order details in sales_order table --- Ends

			assert orderId != null;

			// create order line by inserting the order details in order_line_item table --- Starts
			if (fetchedItemList != null && fetchedItemList.size() >0 ) {
				for (int i=0 ; i < fetchedItemList.size() ; i++) {
					if (fetchedItemList.get(i).getItemName() != null && fetchedItemList.get(i).getItemName() != ""){
						OrderLineItem orderLineItem = new OrderLineItem();
						orderLineItem.setItemName(fetchedItemList.get(i).getItemName());
						orderLineItem.setItemQuantity(fetchedItemList.size());// Check
						orderLineItem.setOrderId(orderId);
		
						orderLineItemRepository.save(orderLineItem);
						
					} else {
						//Item details not available -- Exception to be thrown
						System.out.println("<<<<<<<<<Item details not available 222222222 >>>");
						break;
					}
				}
			}
			// create order line by inserting the order details in order_line_item table --- Ends
		}
		
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setLocation(ServletUriComponentsBuilder
//				.fromCurrentRequest().path("/" + orderId)
//				.buildAndExpand().toUri());

//		return new ResponseEntity<>(orderId, httpHeaders, HttpStatus.CREATED);
		
		return orderId.toString();
	}


	public void insertCustomerSOS(Customer customer) {
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<insertCustomerSOS>>>>>>>>>>>>>>>>>>>>>");
		CustomerSOS customerSOS  = new CustomerSOS();
		
		customerSOS.setCustId((Integer.parseInt(customer.getId())));
		customerSOS.setCustFirstName(customer.getFirstName());
		customerSOS.setCustLastName(customer.getLastName());
		customerSOS.setCustEmail(customer.getEmail());
		
		customerSOSRepository.save(customerSOS);
	}
	
	// This method is for implementing Ribbon - Client side load balancing
	private String fetchItemServiceUrl() {
		
		System.out.println("Inside fetchItemServiceUrl");
		
		ServiceInstance instance = loadBalancerClient.choose("item-service");
		
		System.out.println("After fetching instance in fetchItemServiceUrl");
		
		System.out.println("uri: {}" + instance.getUri().toString());
		System.out.println("serviceId: {}" + instance.getServiceId());

	    return instance.getUri().toString();
	}
	
	public String custItemFallback(SalesOrderDetails salesOrderDetails) {
		System.out.println("Inside custItemFallback method>>>>" + salesOrderDetails.getErrVar());
		if(salesOrderDetails.getErrVar() != null && salesOrderDetails.getErrVar().equalsIgnoreCase("custErr")) {
			return "The customer ID is not available. Please enter a valid customer id.";
		} else {
			return "The item details entered are not valid. Please enter a valid item name.";
		}
		
	}
	
}
