# 🔜 ACG : Auto Commit Program
**깃허브 로그인만으로 하루 2번 자동으로 커밋을 해주는 서비스 (깃허브 잔디 관리자)**
<br>

# 👨🏻‍💻 Contributors
|  <div align = center>조현태 </div> | <div align = center> 소병욱 </div> |
|:----------|:----------|
|<div align = center> <img src = "https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fnoticon-static.tammolo.com%2Fdgggcrkxq%2Fimage%2Fupload%2Fv1567128822%2Fnoticon%2Fosiivsvhnu4nt8doquo0.png&blockId=865f4b2a-5198-49e8-a173-0f893a4fed45&width=256" width = "17" height = "17"/> [hyuntae99](https://github.com/hyuntae99) </div> |<div align = center> <img src = "https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fnoticon-static.tammolo.com%2Fdgggcrkxq%2Fimage%2Fupload%2Fv1567128822%2Fnoticon%2Fosiivsvhnu4nt8doquo0.png&blockId=865f4b2a-5198-49e8-a173-0f893a4fed45&width=256" width = "17" height = "17"/> [Cybecho](https://github.com/Cybecho) </div>|
<br>

## 📖 Development Tech
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
<br>

# 💼 Server Architecture
<img src="https://velog.velcdn.com/images/jmjmjmz732002/post/a6c7a7be-ff27-4723-bfe2-d458ed641fab/image.png">
<br>

# 🗂️ Directory
```
├── java
│   └── com
│       └── project
│           └── ACG
│               ├── AcgApplication
│               ├── config
│               │   ├── SecurityConfig
│               │   ├── SwaggerConfig
│               │   └── WebSocketConfig
│               ├── controller
│               │   ├── GithubApiController
│               │   ├── GlobalController
│               │   ├── GreetingController
│               │   ├── JGitController
│               │   └── UserController
│               ├── entity
│               │   │── Greeting
│               │   │── Message
│               │   │── User
│               │   │── UserDto
│               │   │── UserUpdateRequest
│               │   └── UserUpdateResponse
│               ├── exception
│               │   │── ErrorResponse
│               │   └── GlobalExceptionHandler
│               ├── repository
│               │   └── UserJpaRepositoty
│               └── service
│                   ├── GithubApiService
│                   ├── AutoCommitService
│                   ├── JGitService
│                   └── UserService
└── test
```

# 📝 Service

**동영상을 GIF로 바꾸는 과정이 원할하지 않아서 GIF가 이상한 점 먼저 사과 드립니다.**

## 1. 로그인 및 회원가입
로고를 누르면 Github 쇼셜 로그인이 진행됩니다.<br>
이후 메인 페이지에서 사용자의 계정, 이름, 이메일을 확인할 수 있습니다.<br>

<img src = "https://private-user-images.githubusercontent.com/101180610/316520188-1dcaca7a-847b-4f50-b36c-a9be5062f928.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTE1ODg0NTMsIm5iZiI6MTcxMTU4ODE1MywicGF0aCI6Ii8xMDExODA2MTAvMzE2NTIwMTg4LTFkY2FjYTdhLTg0N2ItNGY1MC1iMzZjLWE5YmU1MDYyZjkyOC5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQwMzI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MDMyOFQwMTA5MTNaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0yZmIwMGYxZTU0NDY5MjA5MTM5OGQxMmUxNDdlYzllZDg0NmU3YTcwYjBjOTEwZTBjOTQ2OWY2NGM5OTE2ZWM1JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.c8gxIlA4YULETdJmTyX4xsDNUtRY5d8fC27mkP8Vek4">
<br>

## 2. 서비스 이용 설정
서비스 이용 버튼을 누르면 사용자의 계정, 이메일, 저장소 이름을 입력받고 앞으로 자동 커밋 서비스를 받을 저장소를 생성합니다. <br>

<img src = "https://private-user-images.githubusercontent.com/101180610/316520399-b22c00ff-c7e3-4d61-80ec-01a2cac693b4.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTE1ODc5NzQsIm5iZiI6MTcxMTU4NzY3NCwicGF0aCI6Ii8xMDExODA2MTAvMzE2NTIwMzk5LWIyMmMwMGZmLWM3ZTMtNGQ2MS04MGVjLTAxYTJjYWM2OTNiNC5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQwMzI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MDMyOFQwMTAxMTRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT00NGYwNDFkMGUwZTc5ZjM1NDQ3ZGY4ZDRhYzAzYWY2ZGY2YTZmOGZiYzhkODU0ZmZkYzgyOGFjNjQzMDIzMzIzJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.zACTWB89jTazyBkRYKy9EFivvbjrrzKRZKy7YGhuWLs">
<br>

## 3. 서비스 설정 후 깃허브 저장소
설정한 이름의 저장소가 생기고 그 즉시 commit이 진행되며 12시간마다 커밋을 진행합니다.<br>
sample이라는 커밋 날짜 데이터를 추가하며 10개의 파일이 넘어갈 경우, 삭제하여 너무 많은 파일이 남지 않도록 합니다.<br>
위와 같은 유의사항 및 안내사항은 생성된 저장소의 우측 링크를 통해서 자세히 확인하실 수 있습니다.<br>

<img src = "https://private-user-images.githubusercontent.com/101180610/316520210-ac1e54d3-2f99-4bcc-bb8d-0f28781375d3.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTE1ODc3NjUsIm5iZiI6MTcxMTU4NzQ2NSwicGF0aCI6Ii8xMDExODA2MTAvMzE2NTIwMjEwLWFjMWU1NGQzLTJmOTktNGJjYy1iYjhkLTBmMjg3ODEzNzVkMy5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQwMzI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MDMyOFQwMDU3NDVaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT02YWI3YjQxNGNhMmE3NGZmNjU0N2QxOTdiMjdkMTc5OTI1Yzg1OWNjMmEwZTMyYzE0ZTE1NjNjODA4NDRkZmQ4JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.tXHRhNcROMJ1vwpT3-RqNHpTjHmAyjlH8LGmCoWQibc">
<br>

## 4. 유저 탈퇴
사용자의 계정과 이메일을 입력받고 사용자를 삭제합니다.<br>
이때, 이미 생성된 저장소는 사라지지 않으며 이 저장소를 삭제할 경우 잔디가 삭제됩니다.<br>
해당 유의사항은 생성된 저장소 우측 url에 접속하면 확인할 수 있습니다.<br>

<img src = "https://private-user-images.githubusercontent.com/101180610/316520300-ecbb2158-23a3-4144-b918-92b9a5027fb0.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTE1ODg0MTQsIm5iZiI6MTcxMTU4ODExNCwicGF0aCI6Ii8xMDExODA2MTAvMzE2NTIwMzAwLWVjYmIyMTU4LTIzYTMtNDE0NC1iOTE4LTkyYjlhNTAyN2ZiMC5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQwMzI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MDMyOFQwMTA4MzRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT04NGY1ZDIzZjQ0NGViNzBkZGJhNmVjYjIzNWJmYjIxMTk5MWVhNmJlOGZmMTkyMzA4ZWE3YzhiNzYxNTU3N2EzJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.yeJGfbUj_h9HCbjPH3ibaw9sWlh-YT5zFY5F3ilQu2Q">
<br>

## 5. 익명 단체 채팅방
사용하는 유저끼리 간단하게 채팅을 할 수 있는 익명 채팅방입니다.<br>
원하는 닉네임으로 입장하여 채팅이 가능하며, 퇴장을 하면 이전 채팅 내역이 모두 사라지는 간단한 채팅방입니다.<br>

<img src = "https://private-user-images.githubusercontent.com/101180610/316520310-5f878a45-3c47-4560-99b1-2c1b6deeea9b.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTE1ODg0MTQsIm5iZiI6MTcxMTU4ODExNCwicGF0aCI6Ii8xMDExODA2MTAvMzE2NTIwMzEwLTVmODc4YTQ1LTNjNDctNDU2MC05OWIxLTJjMWI2ZGVlZWE5Yi5naWY_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjQwMzI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI0MDMyOFQwMTA4MzRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0yZjY0ZmU5MDAzNmY4MDQ1NDBjNzUxNmUxYWNiNjNmYTRiOGJlMjM4OGE3Y2FkYTVkNDFmZDI3MGJmMGVmOWY5JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.hZaiGoF3m7l2-cZSv1QQdYIX3Ji_VbkPEAcb3AFkqtk">
<br>

## 🙋🏻‍♀️ 더 많은 자료는?
### [PPT 발표 자료](https://docs.google.com/presentation/d/1SwTtjPmRu_-K8BIRhXG298PkwFupW2K0/edit?usp=sharing&ouid=103204687067264269924&rtpof=true&sd=true)를 참고해주세요!