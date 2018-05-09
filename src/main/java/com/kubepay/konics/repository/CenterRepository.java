package com.kubepay.konics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.konics.entity.Center;

@Repository("centerRepository")
public interface CenterRepository extends JpaRepository<Center, Integer> {

  List<Center> findByActive(Integer id);
}
