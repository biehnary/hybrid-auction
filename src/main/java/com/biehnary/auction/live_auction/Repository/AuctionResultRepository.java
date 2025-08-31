package com.biehnary.auction.live_auction.Repository;

import com.biehnary.auction.live_auction.entity.AuctionResult;
import com.biehnary.auction.live_auction.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AuctionResultRepository {

  @PersistenceContext
  private EntityManager em;

  // == CURD ==
  public AuctionResult save(AuctionResult auctionResult) {
    if (auctionResult.getId() == null) {
      em.persist(auctionResult);
      return auctionResult;
    } else {
      return em.merge(auctionResult);
    }
  }

  public Optional<AuctionResult> findById(Long id) {
    AuctionResult auctionResult = em.find(AuctionResult.class, id);
    return Optional.ofNullable(auctionResult);
  }

  public List<AuctionResult> findByWinner(Member winner) {
    return em.createQuery("select a from AuctionResult a where a.winner = :winner"
            + " order by a.auctionEndTime desc, a.finalPrice desc"  ,AuctionResult.class)
        .setParameter("winner", winner)
        .getResultList();
  }












}
