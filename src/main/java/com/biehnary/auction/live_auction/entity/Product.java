package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

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

  // == field crud ==
  public static Product createProduct(String name, int price, String imageUrl, String description,
      Member seller) {
    Product product = new Product();
    product.name = name;
    product.price = price;
    product.imageUrl = imageUrl;
    product.description = description;
    product.seller = seller;
    product.auctionStatus = AuctionStatus.WAITING;
    product.registeredAt = LocalDateTime.now();
    return product;
  }

  public void updateProduct(String name, int price, String imageUrl, String description) {
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
    this.description = description;
  }

  public void changeAuctionStatus(AuctionStatus newStatus) {
    this.auctionStatus = newStatus;
  }







}
