package com.biehnary.auction.live_auction.Repository;

import com.biehnary.auction.live_auction.entity.ChatMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageRepository {

  @PersistenceContext
  private EntityManager em;

  public ChatMessage save(ChatMessage message) {
    em.persist(message);
    return message;
  }

  // == find tools ==
  public List<ChatMessage> findRecentMessages(int limit) {
    return em.createQuery("select c from ChatMessage c order by c.sendTime desc",
            ChatMessage.class)
        .setMaxResults(limit)
        .getResultList();
  }

  public Long count() {
    return em.createQuery("select count (c) from ChatMessage c", Long.class)
        .getSingleResult();
  }











}
