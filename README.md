# 🔜 ACG : Auto Commit Program
**깃허브 로그인만으로 하루 2번 자동으로 커밋을 해주는 서비스**
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
