# hybrid_auction

Service 계층 개발
- 메서드 시그니처 정리. 실무 프로세스 고민. 
- Service 계층 분리. Member, Product, ChatMessage
- AuctionService의 분리. BidService (비즈니스 로직), AuctionService (데이터 관리. 결과 ...)

##MemberSerivce 구현
- 권한 기능은 일단 배제
- 간단한 가입, 탈퇴(soft delete), 수정
- 핵심 로직 시연용으로, 보안이나 인증 권한등 배제
- 엔티티 createMember() 추가 > 고민. Entity, Repository, Service 전부 CRUD를 구현하는데 목적과 차이점?
- 


