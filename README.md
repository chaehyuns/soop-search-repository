# GitHub Repository 검색 앱 🌳

Github API를 사용하여 repository를 검색하는 어플리케이션

## 필수 요구 사항 ⚒️

### 개발 환경

- Language : Kotlin
- minSdk : 26
- targetSdk : 34

### Main 화면

- 검색어를 입력하면, Github API를 통해 Repository를 검색한다.
    - Github 검색 API (https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories)
    - 유저 프로필 이미지, Repository명, description, star, 사용언어를 필수로 보여준다.
- 검색된 Repository를 클릭하면, Repository의 상세정보를 보여주는 상세페이지로 이동한다.

### 상세 화면

- Repository 정보 조회 API를 활용하여, Repository의 상세페이지를 구현한다.
    - Repository 조회 API (https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#get-a-repository)
    - 유저 프로필 이미지, 유저명, Repository명, star, watchers, Forks, description를 필수로 보여준다.
    - | 필드 | 설명 |
      | --- | --- |
      | owner.avatar_url | 프로필 이미지 |
      | name | Repository명 |
      | stargazersCount | star count |
      | watchers_count | watchers count |
      | forks_count | forks count |
      | description | repository 정보 |

### BottomSheet

- User의 Repository정보, User정보 API를 활용하여 User정보를 BottomSheet로 표시된다.
    - User 정보 조회 API (https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user)
    - language (repository에 사용된 언어)를 필수로 보여준다.
    - 유저 정보 조회 API (https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user)
    - avatar_url(유저 프로필 이미지), login(유저명), followers(팔로워 수), following(팔로잉 수), forks_count(forks count)를 필수로 보여준다.

## 우대 사항 🌳

- GitHub README.md에 자신이 구현한 코드를 잘 설명한 경우
- 메모리, 성능 등 최적화를 고려한 경우 (어떤 부분을 생각 하고 고려 했는지 README에 설명)
- UnitTest를 작성하여 코드가 올바르게 동작하는 것을 검증한 경우
- 그 외 내부 기준에 따른 추가 점수 반영 (Coroutine, Compose, Jetpack, Architecture, 멀티모듈 등)

## 기타 사항 📌

- 제출 마감 기한: 2월 9일 자정
- 라이브러리는 자유롭게 사용 가능합니다.
- 예시 화면은 참고를 위해서 제공된 것이며, 화면 구성은 자유입니다.
- 요구 사항을 전부 구현하지 않아도 부분 점수가 반영되니 기한 내에 제출해주세요.
- 면접 진행 시 코드에 대한 질문이 있을 수 있습니다.
