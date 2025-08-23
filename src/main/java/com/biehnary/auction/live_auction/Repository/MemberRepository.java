package com.biehnary.auction.live_auction.Repository;

import com.biehnary.auction.live_auction.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

  @PersistenceContext
  private EntityManager em;


  // == CRUD Operations ==
  public Member save(Member member) {
    if (member.getId() == null) {
      em.persist(member);
      return member;
    } else {
      return em.merge(member);
    }
  }

  // (throws exception if not found)
  public Member findById(Long memberId) {
    Member member = em.find(Member.class, memberId);
    if (member == null) {
      throw new IllegalArgumentException("Member not found: " + memberId);
    }
    return member;
  }

  public List<Member> findAll() {
    return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
  }

  public void deleteById(Long memberId) {
    em.remove(findById(memberId));
  }


















}
