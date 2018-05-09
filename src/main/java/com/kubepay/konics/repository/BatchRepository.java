package com.kubepay.konics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.konics.entity.Batch;

@Repository("batchRepository")
public interface BatchRepository extends JpaRepository<Batch, Long> {

  List<Batch> findByCenter(Integer center); 

}
