package com.biehnary.auction.live_auction.service;

import com.biehnary.auction.live_auction.Repository.BidRepository;
import com.biehnary.auction.live_auction.Repository.MemberRepository;
import com.biehnary.auction.live_auction.Repository.ProductRepository;
import com.biehnary.auction.live_auction.entity.AuctionStatus;
import com.biehnary.auction.live_auction.entity.Bid;
import com.biehnary.auction.live_auction.entity.Member;
import com.biehnary.auction.live_auction.entity.Product;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidService {

  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final BidRepository bidRepository;

  // Only create method in v.1
  @Transactional
  public void placeBid(Long productId, Long bidderId, int bidAmount) {
    Product product = productRepository.findById(productId);
    Member bidder = memberRepository.findById(bidderId);

    validateBid(product, bidAmount);
    Bid bid = Bid.createBid(product, bidder, bidAmount);

    bidRepository.save(bid);
  }

  private void validateBid(Product product, int bidAmount) {
    if (product.getAuctionStatus() != AuctionStatus.WAITING &&
        product.getAuctionStatus() != AuctionStatus.LIVE) {
      throw new IllegalStateException("입찰 불가능한 상태입니다.");
    }

    Bid currentHighest = bidRepository.findHighestBidByProduct(product);
    if (bidAmount <= 0 || currentHighest != null && bidAmount <= currentHighest.getBidAmount()) {
      throw new IllegalArgumentException("유효하지 않은 입찰금액입니다.");
    }

    if (bidAmount < product.getPrice()) {
      throw new IllegalArgumentException("시작가보다 높게 입찰해야 합니다.");
    }
  }

  // read
  public Optional<Bid> getCurrentHighestBid(Long productId) {
    Product product = productRepository.findById(productId);
    return Optional.ofNullable(bidRepository.findHighestBidByProduct(product));
  }

  // BidList
  public List<Bid> getRecentBid(Long productId, int limit) {
    Product product = productRepository.findById(productId);
    return bidRepository.findRecentBidsByProduct(product, limit);
  }

  // count bidders
  public Long getBidderCount(Long productId) {
    Product product = productRepository.findById(productId);
    return bidRepository.countDistinctBiddersByProduct(product);
  }

  // logs for bidder
  public List<Bid> getUserBids(Long bidderId) {
    Member member = memberRepository.findById(bidderId);
    return bidRepository.findByBidder(member);
  }










}
