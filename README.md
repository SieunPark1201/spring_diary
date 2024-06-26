## 0. 프로젝트 목표

- ***인공지능 API 사용해보기***
- 프로젝트가 늘어지지 않도록 3~4개의 테이블로 구성된 작은 프로젝트 빠르게 만들기
- 전체 구조를 정교하게 짜두고 시작해보기
- 내가 처음부터 끝까지 잘 만들 수 있는지 확인해보고 취약하거나 부족한 부분들 체크 및 공부
- 전반적 구조는 짤 수 있게 되었으니 이제 보다 효율적인 코드를 고민해보기. 잘 모르겠으면 검색하여 오래오래 파고들기
- **실제로 배포해보고 사용자 피드백도 받아보기**

## 1. 개요

> 일기가 AI에게 어떤 식으로 요약될지 궁금하다! 어쩌면 일기의 요약문이 새로운 인사이트를 줄 지도 모른다. 꽤 시적인 문구가 나올 수도 있는 요약을 프롬프트로 이용하여 인공지능으로 이미지를 생성해본다면, 내 일기의 심상이 어떤 모양인지 들여다보는 꽤 근사한 경험을 할 수 있을 것이다. 가장 사적인 글쓰기인 일기와 AI를 접목시킴으로서 AI 사용이 일상화될 수 있도록 장려한다.
> 
- **서비스명:** 세줄요약좀
- **서비스 주제:** AI API를 이용한 웹다이어리
- **기획 의도 및 목표:** AI 사용의 일상화

## 2. 소프트웨어 설계

- **개발 환경**
    - Java 11
    - Springboot
    - Gradle
    - MySQL
    - Test: Postman
    - JPA
    - Spring Security
    - Docker
    - AWS → 사용해볼지 고민해보기
    

## 3. 서비스 분석

- 회원가입/회원탈퇴
    - 닉네임, 이메일, 비밀번호, 생일을 입력하면 회원가입이 가능하다.
    - 회원정보 수정이 가능하다.
    - 회원 탈퇴 버튼을 통해 회원 목록에서 스스로를 삭제할 수 있다.
    
- 로그인/로그아웃
    - 이메일과 비밀번호를 통해 로그인이 가능하다.
    - 버튼을 통해 로그아웃 또한 가능하다.
    
- 일기 작성/수정/삭제 및 임시저장
    - 특정 날의 일기를 작성/수정/삭제할 수 있다.
    - 임시저장 또한 가능하다. 임시저장된 글을 따로 볼 수 있는 공간이 있다.
    
- 일기 요약
    - ChatGPT3.5의 API를 이용하여 일기를 요약하는 프롬프트를 자동화하고, 그 결과를 데이터로 저장한다.
    - 한 일기에 대해 여러 번 요약/분석할 수 있다.
    

## 4. 서비스 확장(예정)

- 키워드 검색 기능
    - 키워드를 바탕으로 일기의 내용을 검색할 수 있다.
    
- 생일
    - 사용자의 생일이 되면 도메인에 생일을 축하한다는 팝업 메세지가 뜬다.
    
- 일기 월별 분석
    - 토픽 모델링: 일기에 자주 나온 단어를 보여준다.
    - 월 단위 일기를 요약한다.
    
- 일기 공유 서비스
    - 일기의 일부, 일기 전체, 일기 요약본, 일기 이미지를 다른 사람과 공유하는 커뮤니티를 생성한다.
    
- 보안
    - 사적인 글인 만큼 보안을 강화한다
