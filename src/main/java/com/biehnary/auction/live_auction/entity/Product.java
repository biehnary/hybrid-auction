package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private String description;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @Enumerated
    private AuctionStatus auctionStatus;
    private LocalDateTime registeredAt;



}
