package com.biehnary.auction.live_auction.service;

import com.biehnary.auction.live_auction.Repository.MemberRepository;
import com.biehnary.auction.live_auction.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// minimal member management for mvp
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  // Generator injection - spring
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public void join(String username, String password) {
    Member member = Member.createMember(username, password);
    memberRepository.save(member);
  }

  @Transactional
  public void updateMember(Long memberId, String newUsername, String newPassword) {
    Member member = memberRepository.findById(memberId);
    member.changePassword(newPassword);
    member.updateInfo(newUsername);
  }

  // soft delete for logs
  @Transactional
  public void deactivateMember(Long memberId) {
    Member member = memberRepository.findById(memberId);
    member.deactivate();
  }












}
