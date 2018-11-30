package io.online.salesorder.domain;

public class OrderLineItem {
	private Long orderLineItemId;
	private String itemName;
	private Integer itemQuantity;
	private Long orderId;
	
	public OrderLineItem() {};
	
	public OrderLineItem(Long orderLineItemId, String itemName, Integer itemQuantity, Long orderId) {
		super();
		this.orderLineItemId = orderLineItemId;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.orderId = orderId;
	}

	public Long getOrderLineItemId() {
		return orderLineItemId;
	}

	public void setOrderLineItemId(Long orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
