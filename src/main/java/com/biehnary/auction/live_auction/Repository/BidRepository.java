package com.biehnary.auction.live_auction.Repository;

import com.biehnary.auction.live_auction.entity.Bid;
import com.biehnary.auction.live_auction.entity.Member;
import com.biehnary.auction.live_auction.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Map;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidRepository {

  @PersistenceContext
  private EntityManager em;

  // == CURD Operation ==
  // no delete - all bids stay in the system for audit purpose
  public void save(Bid bid) {
    em.persist(bid);
  }

  public Bid findById(Long bidId) {
    Bid bid = em.find(Bid.class, bidId);
    if (bid == null) {
      throw new IllegalArgumentException("Bid not found" + bidId);
    }
    return bid;
  }

  public List<Bid> findAll() {
    return em.createQuery("select b from Bid b", Bid.class)
        .getResultList();
  }


  public Long count() {
    return em.createQuery("select count(b) from Bid b", Long.class)
        .getSingleResult();
  }

  public boolean existsById(Long bidId) {
    Long count = em.createQuery("select count(b) from Bid b where b.id = :id", Long.class)
        .setParameter("id", bidId)
        .getSingleResult();
    return count > 0;
  }

  // == Utilities ==
  public List<Bid> findRecentBidsByProduct(Product product, int limit) {
    return em.createQuery("select b from Bid b where b.product = :product order by b.bidTime desc "
        , Bid.class)
        .setParameter("product", product)
        .setMaxResults(limit)
        .getResultList();
  }

  public Long countDistinctBiddersByProduct(Product product) {
    return em.createQuery("select count(distinct b.bidder) from Bid b where b.product = :product"
            , Long.class)
        .setParameter("product", product)
        .getSingleResult();
  }

  public Bid findHighestBidByProduct(Product product) {
    return em.createQuery("select b from Bid b where b.product = :product order by b.bidAmount desc",
            Bid.class)
        .setParameter("product",product)
        .setMaxResults(1)
        .getSingleResult();
  }

  public List<Bid> findByBidder(Member bidder) {
    return em.createQuery("select b from Bid b where b.bidder = :bidder order by b.bidTime desc",
            Bid.class)
        .setParameter("bidder", bidder)
        .getResultList();
  }














}