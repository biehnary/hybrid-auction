package com.biehnary.auction.live_auction.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.biehnary.auction.live_auction.Repository.BidRepository;
import com.biehnary.auction.live_auction.Repository.MemberRepository;
import com.biehnary.auction.live_auction.Repository.ProductRepository;
import com.biehnary.auction.live_auction.entity.Bid;
import com.biehnary.auction.live_auction.entity.BidType;
import com.biehnary.auction.live_auction.entity.Member;
import com.biehnary.auction.live_auction.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class BidServiceTest {
  @Autowired
  private BidService bidService;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private BidRepository bidRepository;

  @Test
  void 회원가입_입찰생성() {
    //given
    Member seller = Member.createMember("seller", "1234");
    Member bidder = Member.createMember("bidder", "1234");

    memberRepository.save(seller);
    memberRepository.save(bidder);

    Product product = Product.createProduct("book", 20000, "image",
        "좋은 책입니다.", seller);
    productRepository.save(product);

    //when
    bidService.placeBid(product.getId(), bidder.getId(), 20001);

    //then
    assertThat(memberRepository.count()).isEqualTo(2L);
    assertThat(productRepository.count()).isEqualTo(1L);
    assertThat(bidRepository.count()).isEqualTo(1L);

    Bid savedBid = bidRepository.findHighestBidByProduct(product);
    assertThat(savedBid.getBidAmount()).isEqualTo(20001);
    assertThat(savedBid.getBidder().getUsername()).isEqualTo("bidder");
    assertThat(savedBid.getBidType()).isEqualTo(BidType.PRE_BID); // Product가 WAITING 상태
    }







}
