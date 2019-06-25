package com.ing.mortgage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.mortgage.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
