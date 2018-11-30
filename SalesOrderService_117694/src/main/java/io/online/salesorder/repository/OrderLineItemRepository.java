package io.online.salesorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.online.salesorder.entity.OrderLineItem;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long>{

}


