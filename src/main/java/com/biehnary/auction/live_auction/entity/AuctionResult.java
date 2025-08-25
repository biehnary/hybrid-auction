package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AuctionResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "winner_id")
  private Member winner;

  private int finalPrice;
  private LocalDateTime auctionEndTime;

  @Enumerated(EnumType.STRING)
  private ResultStatus status;
}
