package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private LocalDateTime joinDate;
  private Boolean isActive;

  // Methods

  // factory method. it needs to return
  public static Member create(String username, String password) {
    Member member = new Member();
    member.username = username;
    member.password = password;
    member.joinDate = LocalDateTime.now();
    member.isActive = true;
    return member;
  }
}
