package com.biehnary.auction.live_auction.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Member sender;

  private String content;
  private LocalDateTime sendTime;
}
