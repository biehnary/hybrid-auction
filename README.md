# hybrid_auction

## Service 계층 개발
- 메서드 시그니처 정리. 실무 프로세스 고민. 
- Service 계층 분리. Member, Product, ChatMessage
- AuctionService의 분리. BidService (비즈니스 로직), AuctionService (데이터 관리. 결과 ...)

## MemberSerivce 구현
- 권한 기능은 일단 배제
- 간단한 가입, 탈퇴(soft delete), 수정
- 핵심 로직 시연용으로, 보안이나 인증 권한등 배제
- 엔티티 createMember() 추가 > 고민. Entity, Repository, Service 전부 CRUD를 구현하는데 목적과 차이점?
- Repository 생성자 주입의 이유 : final 선언(필드 주입은 왜안되는지), 컴파일 시점 검증 순환참조 검증 타이밍
- lombok세터 세팅. 후에 필드 추가를 고려한 설계로 전체 setter 설정 후 보안 상 중요한 필드들을 제외하는 방식으로 결정

## ProductService
- 구현할 기능?
- 어렵다. 엔터티에 create, update, changeStatus를 넣는게 과연 맞나. Service에서 직접적인 엔터티조작은 피하는게 맞는가..
- 레포지토리의 역할과 정확히 의미상 구분하기 위해서는..
>  일단 프로세스차이고 다들 명확한 기준 절대적 기준으로 나누는건 아닌거라는거 이해했고
  그래도 얼추 의미적으로 잘 구분은 되어야 프로젝트가 중구난방되지않으니까
  repository 절대적으로 db에 오르내리는 변경점.
  엔티티에서는 '필드에 직접 접근 지양'. 다만 기본적인 crud메서드 제공하는거 나쁘지않음
  service에서는 위 두가지를 조합해 실질적 비즈니스 로직을 구현?



## BidService
- entity 메서드작성
- placeBid 작성. validate 로직
- 계층 분리 필요성. controller 에서는 직접적으로 repository 에 접근하지 않고 service 레이어를 통하는게 정석
- 그 이유에 대한 고찰.
- 조회 로직 구현
- 서비스 클래스에 transactional(readOnly = ture)를 붙인 이유. 조회위주의 서비스로직. 데이터 무결성
- 조작성 메서드 작성 시 따로 Transactional 어노테이션 붙이기 가능(placeBid..)

## 중간 단위 테스트
- Mock data
- oracle DB연동
- build.gradle 추가 (pom.xml) > application.properties 추가
- ddl-auto 설정. test환경 create-drop
- 