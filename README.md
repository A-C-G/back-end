# 🔜 ACG : Auto Commit Program
**깃허브 로그인만으로 하루 2번 자동으로 커밋을 해주는 서비스 (깃허브 잔디 관리자)**

**(추가) 현재 서버 이전으로 기존 Linux 서버와의 통신이 어려워 Spring 서버에서 모든 기능을 구현했습니다.**
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
└── resources
    ├── config 
    │   └── application-local.yml
    ├── static
    │   ├── ACG_LOGO_WHITE.png
    │   ├── app.js
    │   └── index.html
    ├── templates
    │   ├── ACG_LOGO.png
    │   ├── chat.html
    │   ├── description.html
    │   ├── info.html
    │   ├── setting.html
    │   ├── success.html
    │   └── withdraw.html
    └── application.yml
```

# 📝 Service

**동영상을 GIF로 바꾸는 과정이 원할하지 않아서 GIF의 화질이 낮은 점 먼저 양해를 구합니다.**

## 1. 로그인 및 회원가입
로고를 누르면 Github 쇼셜 로그인이 진행됩니다.<br>
이후 메인 페이지에서 사용자의 계정, 이름, 이메일을 확인할 수 있습니다.<br>

<img src = "https://velog.velcdn.com/images/hyuntae99/post/1939f7e9-3035-4710-90bf-83a87bdcceb6/image.gif">
<br>

## 2. 서비스 이용 설정
서비스 이용 버튼을 누르면 사용자의 계정, 이메일, 저장소 이름을 입력받고 앞으로 자동 커밋 서비스를 받을 저장소를 생성합니다. <br>

<img src = "https://velog.velcdn.com/images/hyuntae99/post/0e046f01-189a-4734-8cde-57001b20ac2a/image.gif">
<br>

## 3. 서비스 설정 후 깃허브 저장소
설정한 이름의 저장소가 생기고 그 즉시 commit이 진행되며 12시간마다 커밋을 진행합니다.<br>
sample이라는 커밋 날짜 데이터를 추가하며 10개의 파일이 넘어갈 경우, 삭제하여 너무 많은 파일이 남지 않도록 합니다.<br>
위와 같은 유의사항 및 안내사항은 생성된 저장소의 우측 링크를 통해서 자세히 확인하실 수 있습니다.<br>

<img src = "https://velog.velcdn.com/images/hyuntae99/post/e1b0371f-4e90-43df-8848-8f3dacb2577a/image.gif">
<br>

## 4. 유저 탈퇴
사용자의 계정과 이메일을 입력받고 사용자를 삭제합니다.<br>
이때, 이미 생성된 저장소는 사라지지 않으며 이 저장소를 삭제할 경우 잔디가 삭제됩니다.<br>
해당 유의사항은 생성된 저장소 우측 url에 접속하면 확인할 수 있습니다.<br>

<img src = "https://velog.velcdn.com/images/hyuntae99/post/79eb73df-5d04-4863-89da-6ce78381606a/image.gif">
<br>

## 5. 익명 단체 채팅방
사용하는 유저끼리 간단하게 채팅을 할 수 있는 익명 채팅방입니다.<br>
원하는 닉네임으로 입장하여 채팅이 가능하며, 퇴장을 하면 이전 채팅 내역이 모두 사라지는 간단한 채팅방입니다.<br>

<img src = "https://velog.velcdn.com/images/hyuntae99/post/eb271c67-307f-40e5-bcd1-fbbda5d3f323/image.gif">
<br>

## 🙋🏻‍♀️ 더 많은 자료는?
### [PPT 발표 자료](https://docs.google.com/presentation/d/1SwTtjPmRu_-K8BIRhXG298PkwFupW2K0/edit?usp=sharing&ouid=103204687067264269924&rtpof=true&sd=true)를 참고해주세요! 