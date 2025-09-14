# hybrid_auction



findProductsBidByBidder
모든 입찰내역? 혹은 상품별 입찰내역 (해당 상품에 대한 가장 최신의 입찰? distinct?)
어차피 레포지토리에있는건 서비스에서 죄다 덧붙여서 원하는 로직을 만드는건가능함.
최적화의 문제가 있긴한데... 나중에 생각하고 레포지토리는 간단하게하자.
맵구조도 다빼고 그냥 성능최적화는 디폴트를 시간순으로 정하는거정도만 해놓고, 실제 작성 후 빈도와
병목이런걸 따져서 동적소팅을 서비스에 놔둘지, 레포지토리로 옮길지 이런걸 고민하는게 맞는듯?

---

## AuctionResultRepository
### 구현 메서드리스트
- [x] save()
- save의 관례, 저장한 객체반환, 중복확인
- [x] findById(Long id)
- 옵셔널 도입. 옵셔널의 의의, 명확성, 헬퍼메서드
- Optional.empty() 의 의의. 값이 없음을 명확히하는 객체. 조건문으로 에외처리하지않고 예외안전,
- 엠티 값을 받았을 때 명확히 실행할 수 있는 헬퍼메서드의 존재. orElse등 
- 성능의 이점을 보려고 만든 클래스가아니라 버그를줄이고, 예외에서 안전하고 명확한 의도와 가독성을 높이기위한 도구!
- [x] findByWinner(Member winner)
- 기본 정렬순서를 준것이 유효타. 메서드 자체의 존재의미에 대해서도 고민했음. 낙찰자를 기준으로 결과를 반환하는 것이 의미가있을까?
- 자신이 입찰한 모든 물품에 대한 리스트를 반환받고, 거기에 낙찰 여부를 표시하는 것이 훨씬 ux적으로 좋아보이지만
- '나 자신의' 낙찰 리스트도 볼 여지가 있기에 일단 구현을 한다.
- 시간순 정렬을 디폴트로한다. (낙찰 시점) 하지만 라이브 물품이 아닌 경우 같은 시점에 낙찰이 결정되는 프로세스를 가지기 떄문에,
- 동일한 시간에 한해 낙찰금액을 기준으로 소팅하기로 헀다. 이미 엔티티가 가진 필드를 활용했고, 해당 물품들에 대한 소팅이
- 크게 유의미하진 않기 때문에 알파벳순서보다는 낙찰금액 순이 더 맞다고 판단했음.
- [x] findByProduct
- 재경매를 고려한 List반환. 최근 결과 순으로 나열
- [ ] findByProductNameContaining 
- findByProductNameContaining을 구현하는데
1. ProductRepository에 있ㄴ느 findByNameContaining + AuctionResultRepository의 findByProduct 결합.
2. Join 쿼리 작성
- 2번 선택. 1번은 모든 상품을 일단 로드하는데서 오는 부하, 만에하나 여러 상품을 동시 호출하는 인터페이스가 있다면, N+1문제 생김.
- 상품뒤지고 > +1 거기서 리절트찾기. 만약 상품이많으면 n번 리절트를 또 찾는 쿼리가 날아감.
- 해당기능의 용도와 예상되는 빈번한 호출. join쿼리 학습을 위해서 후자로 선택
- 

---

### 단일쿼리로 입찰 물품 중 낙찰 표시하는법...

좋습니다 근데 이 winner를 사용해서 내가 입찰한 product 목록을 반환했을떄 나의 낙찰여부를 좀더 간단히 구현할수있을지요?
아무래도 리스트 중 위너와 조회한 멤버가 같은지 비교하고 이건 그냥 봐도 좀 로직이 썩 좋아보이지않는데 보통 이런식으로 구현이 되나요?

단일 쿼리 방식의 논리
이 방법은 Bid와 AuctionResult 테이블을 조인하고, CASE 문을 사용하여 결과를 한 번에 가져오는 것입니다.
SELECT 절: Product 엔티티와 함께,
해당 상품의 낙찰자(AuctionResult.winner)가 현재 조회하는 Member와 같은지 확인하는 boolean 값을 선택합니다.
FROM 절: Bid 엔티티에서 시작해 Product와 AuctionResult 엔티티를 조인합니다.
WHERE 절: 현재 Member가 입찰한 상품들만 필터링합니다.
이러한 로직을 JPQL 쿼리로 구현하면 다음과 같은 형태가 됩니다.

SELECT p, CASE WHEN ar.winner = :member THEN true ELSE false END
FROM Bid b
JOIN b.product p
LEFT JOIN AuctionResult ar ON ar.product = p
WHERE b.bidder = :member
SELECT p: 입찰한 상품을 선택합니다.

CASE WHEN ar.winner = :member THEN true ELSE false END: AuctionResult의 winner가 현재 조회하는 member와 같으면 true를,
아니면 false를 반환합니다. 이것이 바로 낙찰 여부를 알려주는 플래그 역할을 합니다.
LEFT JOIN: 낙찰 결과(AuctionResult)가 없는 상품도 결과에 포함시키기 위해 LEFT JOIN을 사용합니다.
WHERE b.bidder = :member: 현재 멤버가 입찰한 기록만 필터링합니다.
이렇게 하면, 서비스 계층에서는 단 하나의 쿼리를 호출하고, 반환된 리스트를 받아서 바로 UI에 표시할 수 있습니다. 쿼리 자체가 모든 로직을 처리해주므로, 
별도의 반복문이나 추가적인 데이터베이스 호출이 필요 없습니다.

---

## ChatMessageRepository
1. save 효용성, 설계 선택.
2. /입찰 [금액] 따위의 채팅 명령어를 구현해볼 것인가?
3. 