package com.biehnary.auction.live_auction.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BidRepository {

  @PersistenceContext
  private EntityManager em;


}
