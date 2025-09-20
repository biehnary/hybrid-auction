package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE) private Long id;

  private String username;
  @Setter(AccessLevel.NONE) private String password;
  @Setter(AccessLevel.NONE) private LocalDateTime joinDate;
  @Setter(AccessLevel.NONE) private Boolean isActive;

  // ==Methods==

  // factory method. it needs to return
  public static Member createMember(String username, String password) {
    Member member = new Member();
    member.username = username;
    member.password = password;
    member.joinDate = LocalDateTime.now();
    member.isActive = true;
    return member;
  }

  public void changePassword(String password) {
    this.password = password;
  }

  // member activation
  public void activate() {
    this.isActive = true;
  }

  public void deactivate() {
    this.isActive = false;
  }












}

