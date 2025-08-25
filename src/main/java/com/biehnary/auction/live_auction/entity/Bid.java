package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int bidAmount;

  @Enumerated(EnumType.STRING)
  private BidType bidType;

  private LocalDateTime bidTime;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;


  @ManyToOne
  @JoinColumn(name = "bidder_id")
  private Member bidder;


}
