package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int bidAmount;

  @Enumerated(EnumType.STRING)
  private BidType bidType;

  private LocalDateTime bidTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bidder_id")
  private Member bidder;


}
