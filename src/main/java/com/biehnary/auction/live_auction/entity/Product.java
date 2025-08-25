package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int price;
  private String imageUrl;
  private String description;

  @ManyToOne
  @JoinColumn(name = "seller_id")
  private Member seller;

  @Enumerated(EnumType.STRING)
  private AuctionStatus auctionStatus;
  private LocalDateTime registeredAt;


}
