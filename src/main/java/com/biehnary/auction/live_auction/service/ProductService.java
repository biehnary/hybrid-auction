package com.biehnary.auction.live_auction.service;

import com.biehnary.auction.live_auction.Repository.ProductRepository;
import com.biehnary.auction.live_auction.entity.AuctionStatus;
import com.biehnary.auction.live_auction.entity.Member;
import com.biehnary.auction.live_auction.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public Product registerProduct(String name, int price, String imageUrl,
      String description, Member seller) {
    Product product = Product.createProduct(name, price, imageUrl, description, seller);
    return productRepository.save(product);
  }

  @Transactional
  public void updateProduct(Long productId,String name, int price, String imageUrl,
      String description) {
    Product product = productRepository.findById(productId);
    product.updateProduct(name, price, imageUrl, description);
  }

  @Transactional
  public void deleteProduct(Long productId) {
    Product product = productRepository.findById(productId);
    // 제한사항
    if (product.getAuctionStatus() == AuctionStatus.LIVE) {
      throw new IllegalStateException("진행 중인 경매는 삭제할 수 없습니다.");
    }

    productRepository.delete(product);
  }

  @Transactional
  public void changeAuctionStatus(Long productId, AuctionStatus status) {
    Product product = productRepository.findById(productId);
    product.changeAuctionStatus(status);
  }








}
