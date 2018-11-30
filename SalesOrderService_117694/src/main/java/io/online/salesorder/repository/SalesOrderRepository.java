package io.online.salesorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.online.salesorder.entity.SalesOrder;


public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

}
