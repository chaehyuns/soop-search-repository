# GitHub Repository 검색 앱 🌳

> GitHub API를 활용하여 GitHub Repository 검색 및 상세 정보 조회 기능을 제공하는 앱입니다.
> 사용자는 검색어를 입력하면 Paging3 기반의 무한 스크롤을 통해 검색 결과를 확인할 수 있습니다.
> Google 권장 아키텍처 (MVVM + Repository 패턴)을 따르고 있습니다.
> 또한, Hilt 기반의 DI, Jetpack Compose UI, Coroutine & Flow, Junit5 등을 활용한 비동기 처리, 네트워크 캐싱 최적화 등을 적용하여
> 성능과 유지보수성을 높였습니다.

## 필수 요구 사항 ⚒️

### Main 화면

- [x] 검색어를 입력하면, Github API를 통해 Repository를 검색한다.
    - [Github 검색 API](https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories)
    - 유저 프로필 이미지, Repository명, description, star, 사용언어를 필수로 보여준다.
- [x] 검색된 Repository를 클릭하면, Repository의 상세정보를 보여주는 상세페이지로 이동한다.

### Detail 화면

- [x] Repository 정보 조회 API를 활용하여, Repository의 상세페이지를 구현한다.
    - [Repository 조회 API](https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#get-a-repository)
    - 유저 프로필 이미지(owner.avatar_url), 유저명(owner.login), Repository명(name), star(stargazersCount),
      watchers(
      watchers_count), Forks(forks_count), description를 필수로 보여준다.

### Profile BottomSheet 화면

- [x] User의 Repository정보, User정보 API를 활용하여 User정보를 BottomSheet로 표시된다.
    - [User 정보 조회 API](https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user)
    - language (repository에 사용된 언어)를 필수로 보여준다.
    - [유저 정보 조회 API](https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user)
    - avatar_url(유저 프로필 이미지), login(유저명), followers(팔로워 수), following(팔로잉 수), forks_count(forks
      count)를 필수로 보여준다.

## 🎥 시연 영상

화면 구성 및 네트워크 에러시 캐시 기능 확인

https://github.com/user-attachments/assets/30d3b61d-890c-4893-a248-0f3a418ba101

### 개발 환경

- Language : Kotlin
- minSdk : 26
- targetSdk : 34

## 🚀 메모리, 성능 등의 최적화 사항

### 1️⃣ 효율적인 Paging3 활용

- PagingSource에서 GitHub API의 Pagination을 활용하여 불필요한 네트워크 요청을 최소화했습니다.
- GitHub API의 link 헤더에서 rel="next" 정보를 파싱하여 자동으로 다음 페이지를 요청하도록 구현했습니다.
- 로드 상태(LoadState)를 관리하여 로딩, 에러, 빈 데이터 UI 처리를 최적화했습니다.

### 2️⃣ 네트워크 캐싱 최적화 (OkHttp 캐시)

- GitHub API 검색 기능은 검색어에 따라 동일한 API 요청이 반복될 가능성이 높습니다.
- 이를 해결하기 위해 OkHttp 캐시 및 네트워크 인터셉터를 활용한 캐싱 전략을 적용했습니다.
- OkHttp는 내부적으로 LRU (Least Recently Used) 알고리즘을 사용하여 캐시를 관리합니다.
- OkHttp의 캐시 기능을 사용하여 동일한 검색어에 대한 API 요청을 캐싱함으로써 불필요한 네트워크 호출을 줄였습니다.

### LRU (Least Recently Used) 캐싱 알고리즘이란?

LRU 알고리즘은 가장 오랫동안 사용되지 않은 데이터를 제거하여 새로운 데이터를 저장하는 방식입니다.
즉, 캐시가 가득 찼을 때 최근에 사용되지 않은 항목부터 삭제하여 공간을 확보하는 방식입니다.

### 📌 LRU 알고리즘 동작 방식

- 사용자가 특정 검색어를 입력하여 API 요청을 수행하면, OkHttp는 로컬 캐시를 먼저 확인(Intercepter)합니다.
- 캐시에 저장된 데이터가 존재하고 만료되지 않았다면, API 요청 없이 캐시된 데이터를 반환합니다.
- 캐시가 가득 찬 경우, 가장 오래 사용되지 않은 데이터를 삭제(LRU)하여 새로운 데이터를 저장합니다.
- 최신 데이터가 필요할 경우, 네트워크 요청을 수행한 후 결과를 LRU 캐시에 저장합니다.

> OkHttp의 Cache-Control을 활용하여 온라인/오프라인 상태에 따라 적절한 캐시 정책을 설정

### 온라인 상태 (네트워크 연결 시)

최대 5분(max-age=300) 동안 캐시 유지
동일한 검색어에 대한 반복 요청을 최소화

``` kotlin
fun provideNetworkCacheInterceptor(): Interceptor = Interceptor
```

### 오프라인 상태 (네트워크 연결 없음)

최대 24시간(max-stale=86400)까지 캐시된 데이터 제공
인터넷이 없는 환경에서도 이전에 조회한 데이터 활용 가능

``` kotlin
fun provideOfflineCacheInterceptor(@ApplicationContext context: Context): Interceptor
```

### LRU 캐시 적용의 장점

- 불필요한 네트워크 요청 감소 → API 요청 횟수를 줄이고, 앱 성능 최적화
- 빠른 데이터 제공 → 동일한 검색 결과에 대해 API 응답 속도를 향상
- 오프라인 지원 강화 → 네트워크가 없을 때도 최근 검색 결과를 표시 가능
- 자동 캐시 관리 → 오래된 데이터부터 자동 삭제하여 최적의 캐시 공간 활용

## ✅ UnitTest 작성

[UnitTest PR 내용](https://github.com/chaehyuns/soop-search-repository/pull/24)

- 네트워크 캐시 test 구현 (MockWebServer 활용)
- Fake Repository 및 Erro Repository 구현
- ViewModel UnitTest 작성 (Main, Detail, Profile)

### test 결과

<img width="700" alt="스크린샷 2025-02-09 오후 7 41 48" src="https://github.com/user-attachments/assets/f0107819-3768-4fc0-9373-54fd1fe1ac0b" />

### Jacoco test coverage 계산

<img width="700" alt="image" src="https://github.com/user-attachments/assets/f536a346-77a8-422d-8fc5-5ded9de636ce" />

## 🧐 추가 기능 및 신경 쓴 부분

### Jetpack Compose + Paging3 + Navigation

- Paging3를 활용하여 무한 스크롤을 최적화
- Jetpack Compose UI로 화면을 구성하여 XML 없이 선언적 UI 작성
- Accompanist Navigation을 사용하여 BottomSheet 및 화면 전환을 최적화

### Hilt 기반의 DI 적용

- Hilt를 사용하여 의존성 주입을 최적화하고, 객체 생명주기 관리를 자동화

### 네트워크 예외 처리 및 에러 핸들링

- safeApiCall()을 적용하여 네트워크 예외 발생 시 글로벌 에러 핸들링을 수행
- 네트워크 에러 발생 시, 에러 메시지를 사용자에게 표시하여 UX를 향상 및 재시도 기능 제공

### 공통 컴포넌트를 사용하여 상태별 UI 처리

- LoadingScreen, ErrorScreen 등의 공통 컴포넌트를 사용하여 상태별 UI 처리를 통일화

### 다크 모드 대응 및 검색시 발생하는 여러 case 대응

- 다크 모드에서도 UI가 깨지지 않도록 테마 설정 및 색상 조정
- ui state를 활용한 검색어가 없는 경우 및 error loading 등 다양한 상태 처리

https://github.com/user-attachments/assets/b6326e87-ffe5-4017-a940-54b657eec4de

## 기타 사항 📌

Githun Api 사용 방법

* [GitHub API](https://docs.github.com/en/rest)

> Github API 는 인증을 하지 않아도 사용 가능하다. 하지만, 인증을 하지 않으면 요청이 1시간에 60번으로 제한된다. (401 또는 403 에러 발생)
> 인증을 할 경우 1시간에 5000번까지 요청할 수 있다.

인증 방법

https://github.com/settings/tokens 에서 repo와 user에 대한 권한을 주고 personal access token을 생성

local.properties 에 아래와 같이 작성한다
`github_api_key=ghp_xxxxxxxxxxxxxxxxxxxxxxxx`~~
