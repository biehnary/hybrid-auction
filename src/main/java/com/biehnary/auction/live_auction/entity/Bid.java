package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(indexes = {@Index(name = "Idx_product_bidTime", columnList = "product_id, bidTime"),
                  @Index(name = "Idx_product_bidAmount", columnList = "product_id, bidAmount")})
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

  // methods
  public static Bid createBid(Product product, Member bidder, int bidAmount) {
    Bid bid = new Bid();
    bid.product = product;
    bid.bidder = bidder;
    bid.bidAmount = bidAmount;
    bid.bidTime = LocalDateTime.now();
    bid.bidType = (product.getAuctionStatus() == AuctionStatus.WAITING)
        ? BidType.PRE_BID : BidType.LIVE_BID;
    return bid;
  }







}
