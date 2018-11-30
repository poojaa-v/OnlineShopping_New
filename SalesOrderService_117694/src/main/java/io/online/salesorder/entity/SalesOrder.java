package io.online.salesorder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SalesOrder {
	@Id
	@Column(name="id")
	@GeneratedValue
	private Long orderId;
	
	@Column(name="order_date")
	private String orderDate;
	
	@Column(name="cust_id")
	private Integer custId;
	
	@Column(name="order_desc")
	private String orderDesc;
	
	@Column(name="total_price")
	private double totalPrice;

	public SalesOrder() {};
	
	public SalesOrder(Long orderId, String orderDate, Integer custId, String orderDesc, double totalPrice) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.custId = custId;
		this.orderDesc = orderDesc;
		this.totalPrice = totalPrice;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
			
}
