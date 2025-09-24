package com.biehnary.auction.live_auction.service;

import com.biehnary.auction.live_auction.Repository.ProductRepository;
import com.biehnary.auction.live_auction.entity.AuctionStatus;
import com.biehnary.auction.live_auction.entity.Member;
import com.biehnary.auction.live_auction.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product registerProduct(String name, int price, Member seller, String imageUrl,
      String description) {

    return null;
  }

  public void updateProduct(Long productId,String name, int price, Member seller, String imageUrl,
      String description) {


  }

  public void deleteProduct(Long productId) {

  }

  public void changeAuctionStatus(Long productId, AuctionStatus status) {


  }








}
