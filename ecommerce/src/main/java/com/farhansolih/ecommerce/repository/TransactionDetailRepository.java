package com.farhansolih.ecommerce.repository;

import com.farhansolih.ecommerce.model.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, UUID> {}