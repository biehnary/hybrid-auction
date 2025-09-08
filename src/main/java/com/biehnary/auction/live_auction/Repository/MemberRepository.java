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

  public void delete(Member member) {
    if (em.contains(member)) {
      em.remove(member);
    } else {
      // Merge detached entity before removal to avoid exceptions
      em.remove(em.merge(member));
    }
  }

  public void deleteById(Long memberId) {
    em.remove(findById(memberId));
  }

  public Long count() {
    return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
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

  public boolean existsById(Long memberId) {
    Long count = em.createQuery("select count(m) from Member m where m.id = :id", Long.class)
        .setParameter("id", memberId)
        .getSingleResult();
    return count > 0;
  }

  public boolean existsByUsername(String username) {
    Long count = em.createQuery("select count(m) from Member m where m.username = :username", Long.class)
        .setParameter("username", username)
        .getSingleResult();
    return count > 0;
  }

  public Member findByUserName(String username) {
    List<Member> results = em.createQuery("select m from Member m where m.username = :username", Member.class)
        .setParameter("username", username)
        .getResultList();
    return results.isEmpty() ? null : results.getFirst();
  }

  public List<Member> findByIsActiveTrue() {
    return em.createQuery("select m from Member m where m.isActive = true", Member.class)
        .getResultList();
  }


}
