# Todo List Application

Du an quan ly cong viec (Todo List) duoc xay dung bang Spring Boot. Day la bai test danh cho vi tri Intern Developer.

## Tinh nang
- Them, sua, xoa cong viec.
- Danh dau cong viec hoan thanh / chua hoan thanh.
- Loc va tim kiem cong viec.
- Phan trang du lieu.

## Cong nghe su dung
- Backend: Java 21, Spring Boot 3.5, Spring Data JPA, MySQL.
- Khac: MapStruct, Swagger, Docker, Docker Compose, JUnit 5, Mockito.

## Huong dan cai dat va chay ung dung

Du an su dung MySQL làm database chinh. Thong tin ket noi duoc cau hinh trong file .env.
Ban can co san MySQL chay o cong 3306 hoac su dung Docker de khoi tao.

### Cach 1: Chay truc tiep bang Maven (Can cai dat MySQL local)
1. Yeu cau he thong da cai dat Java 21 va MySQL (tao database ten la `tododb`).
2. Kiem tra va cap nhat file `.env` voi username va password cua MySQL.
3. Mo Terminal/Command Prompt tai thu muc goc cua du an.
4. Chay lenh sau de khoi dong:
   ./mvnw spring-boot:run
   (Voi Windows, su dung mvnw.cmd spring-boot:run)
5. API se chay o: http://localhost:8080
6. Swagger UI: http://localhost:8080/swagger-ui/index.html

### Cach 2: Chay bang Docker Compose
Cach nay se chay ung dung va database MySQL trong container, rat tien loi neu ban chua cai MySQL.

1. Yeu cau he thong da cai dat Docker va Docker Compose.
2. Mo Terminal/Command Prompt tai thu muc goc cua du an.
3. Build du an (de tao file .jar):
   ./mvnw clean install -DskipTests
4. Chay docker-compose:
   docker-compose up --build
5. API se chay o: http://localhost:8080

## Chay Unit Test
De chay cac Unit Test, su dung lenh:
./mvnw test
