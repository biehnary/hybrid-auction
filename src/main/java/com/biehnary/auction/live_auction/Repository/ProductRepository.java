package com.biehnary.auction.live_auction.Repository;

import com.biehnary.auction.live_auction.entity.AuctionStatus;
import com.biehnary.auction.live_auction.entity.Member;
import com.biehnary.auction.live_auction.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

  @PersistenceContext
  private EntityManager em;

  // == CRUD Operations ==
  public Product save(Product product) {
    if (product.getId() == null) {
      em.persist(product);
      return product;
    } else {
      // em.merge() returns a new persistent entity, not the original
      return em.merge(product);
    }
  }

  public Product findById(Long productId) {
    Product product = em.find(Product.class, productId);
    if (product == null) {
      throw new IllegalArgumentException("Product not found" + productId);
    }
    return product;
  }

  public List<Product> findAll() {
    return em.createQuery("select p from Product p", Product.class)
        .getResultList();
  }

  public void delete(Product product) {
    if (em.contains(product)) {
      em.remove(product);
    } else {
      // Merge detached entity before removal to avoid exceptions
      em.remove(em.merge(product));
    }
  }

  public void deleteById(Long productId) {
    em.remove(findById(productId));
  }

  public Long count() {
    return em.createQuery("select count(p) from Product p", Long.class)
        .getSingleResult();
  }

  public boolean existsById(Long productId) {
    Long result = em.createQuery("select count(p) from Product p where p.id = :id", Long.class)
        .setParameter("id", productId)
        .getSingleResult();
    return result > 0;
  }

  // == Auction Status ==
  public List<Product> findByAuctionStatus(AuctionStatus auctionStatus) {
    return em.createQuery("select p from Product p where p.auctionStatus = :status", Product.class)
        .setParameter("status", auctionStatus)
        .getResultList();
  }

  // == seller ==
  public List<Product> findBySeller(Member seller) {
    return em.createQuery("select p from Product p where p.seller = :seller", Product.class)
        .setParameter("seller", seller)
        .getResultList();
  }

  public List<Product> findAllOrderByRegisteredAt() {
    return em.createQuery("select p from Product p order by p.registeredAt asc", Product.class)
        .getResultList();
  }

  public List<Product> findByNameContaining(String keyword) {
    return em.createQuery("select p from Product p where p.name like :keyword", Product.class)
        .setParameter("keyword", "%" + keyword + "%")
        .getResultList();
  }













}
