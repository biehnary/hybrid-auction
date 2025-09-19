package com.biehnary.auction.live_auction.service;

import com.biehnary.auction.live_auction.Repository.MemberRepository;
import com.biehnary.auction.live_auction.entity.Member;
import org.springframework.stereotype.Service;

// minimal member management for mvp
@Service
public class MemberService {

  MemberRepository memberRepository = new MemberRepository();

  public void join(String username, String password) {
    Member member = new Member();


  }


}
