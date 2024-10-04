# 일일 스크럼 앱 구현하기
## 설명
- 팀원들과 공유하는 일일스크럼을 구현한다고 가정
- 팀원들 중 동명이인이 있을 수 있으므로, 이메일을 통해서 구분할 수 있다.
- 게시글의 작성자명을 변경하면 해당 게시글에 대한 작성자명만 변경된다. (작성자명은 이메일에 매칭되어 있는 것이 아니라, 게시글에 매칭되어 있다)

<br>

## 사용 기술 스택
Java, Spring, JDBC, MySQL, HTML, JavaScript, CSS

<br>

## API
### POST /api/schedule (일정 생성)

- **Request**
    
    ---
    
    json body
    
    ```
    - (필수) username : str  (게시물 작성 당시 작성자 이름)
    - (필수) email : str (게시물 작성자 이메일 정보)
    - (필수) password : str (게시물 패스워드)
    - (필수) title : str (게시물 제목)
    - (필수) description : str (게시물 내용)
    ```
    
    요청 예시
    
    ```
    {
        "username": "맹주희",
        "email": "juhee.bda@gmail.com",
        "password": "1234",
        "title": "2024.10.02 제목 테스트",
        "description": "일정 관리 앱 구현하기"
    }
    ```
    
- **Response**
    
    ---
    
    응답 예시
    
    ```
    {
        "user_id": 1, (int, 작성자 id)
        "schedule_id": 2, (int, 게시물 id)
        "username": "맹주희", (str, 게시물 작성 당시 작성자 이름)
        "email": "juhee.bda@gmail.com", (str, 게시물 작성자 이메일 정보)
        "title": "2024.10.02 제목 테스트", (str, 게시물 제목)
        "description": "일정 관리 앱 구현하기", (str, 게시물 내용)
        "created_at": 1727885971, (int, 게시물 생성 날짜, timestamp 형식)
        "updated_at": 1727885971 (int, 게시물 최종 업데이트 날짜, timestamp 형식)
    }
    ```
    

### GET /api/schedule (일정 조회)

- **설명**
    
    ---
    
    - 수정일(date)가 일치하거나 작성자명(username)이 일치하면 조회한다.
    - 수정일 기준으로 내림차순 정렬하여 조회한다.
- **Request**
    
    ---
    
    query parameter
    
    ```
    - (선택) date: str (조회하고자 게시물의 등록 날짜)
    - (선택) username: str (조회하고자 하는 게시물의 작성자명)
    ```
    
    예시
    
    ```
    - date: 2024-10-03
    - username: 맹주희
    ```
    
- **Response**
    
    ---
    
    예시
    
    ```
    [
        {
            "user_id": 1,
            "schedule_id": 3,
            "username": "길동",
            "email": "juhee.bda@gmail.com",
            "title": "제목입니다",
            "description": "안녕하세요",
            "created_at": 1727967727,
            "updated_at": 1727967727
        },
        {
            "user_id": 1,
            "schedule_id": 2,
            "username": "맹주희",
            "email": "juhee.bda@gmail.com",
            "title": "2024.10.02 제목 테스트",
            "description": "일정 관리 앱 구현하기",
            "created_at": 1727885971,
            "updated_at": 1727885971
        },
        {
            "user_id": 1,
            "schedule_id": 1,
            "username": "맹주희",
            "email": "juhee.bda@gmail.com",
            "title": "2024.10.02 제목 테스트",
            "description": "일정 관리 앱 구현하기",
            "created_at": 1727885956,
            "updated_at": 1727885956
        }, ...
    ]
    ```
    

### GET /api/schedule/{id} (선택 일정 조회)

- **Response**
    
    ---
    
    예시
    
    ```
    {
        "user_id": 1,
        "schedule_id": 2,
        "username": "맹주희",
        "email": "juhee.bda@gmail.com",
        "title": "2024.10.02 제목 테스트",
        "description": "일정 관리 앱 구현하기",
        "created_at": 1727885971,
        "updated_at": 1727885971
    }
    ```
    

### PUT /api/schedule/{id} (선택 일정 수정)

- **설명**
    
    ---
    
    - 작성자명, 내용만 수정 가능
    - 서버 요청시 패스워드 함께 전달 (패스워드가 일치해야 수정 가능)
- **Request**
    
    ---
    
    json body
    
    ```
    - (필수) username : str  (게시물 작성 당시 작성자 이름)
    - (필수) email : str (게시물 작성자 이메일 정보)
    - (필수) password : str (게시물 패스워드)
    ```
    
    예시
    
    ```
    {
        "username": "맹주희",
        "description": "일정 관리 앱 구현하기",
        "password": "1234"
    }
    ```
    
- **Response**
    
    ---
    
    응답 예시
    
    ```
    {
        "user_id": 1, (int, 작성자 id)
        "schedule_id": 2, (int, 게시물 id)
        "username": "맹주희", (str, 게시물 작성 당시 작성자 이름)
        "email": "juhee.bda@gmail.com", (str, 게시물 작성자 이메일 정보)
        "title": "2024.10.02 제목 테스트", (str, 게시물 제목)
        "description": "일정 관리 앱 구현하기", (str, 게시물 내용)
        "created_at": 1727885971, (int, 게시물 생성 날짜, timestamp 형식)
        "updated_at": 1727885971 (int, 게시물 최종 업데이트 날짜, timestamp 형식)
    }
    ```
    
- **ERROR**
    
    ---
    
    400 에러 발생 케이스
    
    - 해당 id의 일정 게시글이 존재하지 않는 경우
    - 패스워드 불일치하는 경우

### DELETE /api/schedule/{id} (선택 일정 삭제)

- **설명**
    
    ---
    
    성공시 200 status code, 반환값은 삭제된 schedule_id
    
    그 외 실패
    
- **Request**

    ---
  
    json body
    
    ```
    - (필수) password : str (게시물 패스워드)
    ```
    
    예시
    
    ```
    {
        "password": "1234"
    }
    ```
    
- **Response**
    
    ---
    
    예시
    
    ```
    2 (int, 삭제된 일정 게시물 id)
    ```
    
- **ERROR**
    
    ---
    
    400 에러 발생 케이스
    
    - 해당 id의 일정 게시물이 존재하지 않는 경우
    
    500 에러 발생 케이스
    
    - DB 이슈로 일정 게시물 비활성 처리가 제대로 되지 않은 경우

<br>

## ERD
![image](https://github.com/user-attachments/assets/b4fab79f-41cc-45f1-8425-20e40806f3ae)
