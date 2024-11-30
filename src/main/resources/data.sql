INSERT INTO member (name, login_id, password, contact)
VALUES ('asdf', 'asdf', '$2a$10$eeL0Qnrah6l3ZPTA2zSGtuQmSIOWe8./iGvuEkRKBvgQAWvZHmY6m', 'asdf');

INSERT INTO category (id, name)
VALUES (1, '전자제품'),
       (2, '도서'),
       (3, '의류'),
       (4, '장난감'),
       (5, '가정 및 주방');

INSERT INTO product (id, name, description, category_id, image_url, stock, price)
VALUES (1, '스마트폰', '최신 기능을 갖춘 고품질 스마트폰.', 1, 'https://picsum.photos/200', 100, 799),
       (2, '노트북', '게임 및 업무용 고성능 노트북.', 1, 'https://picsum.photos/200', 50, 1200),
       (3, '헤드폰', '소음 제거 기능과 뛰어난 음질을 자랑하는 헤드폰.', 1, 'https://picsum.photos/200', 200, 299),
       (4, 'SF 소설', '베스트셀러 과학 소설.', 2, 'https://picsum.photos/200', 150, 19),
       (5, '요리책', '세계 각국의 요리법을 소개하는 요리책.', 2, 'https://picsum.photos/200', 100, 25),
       (6, '티셔츠', '편안한 면 소재의 티셔츠.', 3, 'https://picsum.photos/200', 300, 15),
       (7, '청바지', '스타일리시하고 내구성이 좋은 청바지.', 3, 'https://picsum.photos/200', 200, 40),
       (8, '드레스', '특별한 날을 위한 우아한 드레스.', 3, 'https://picsum.photos/200', 50, 60),
       (9, '액션 피규어', '디테일이 살아있는 수집용 액션 피규어.', 4, 'https://picsum.photos/200', 100, 30),
       (10, '보드게임', '온 가족이 함께 즐길 수 있는 보드게임.', 4, 'https://picsum.photos/200', 150, 50),
       (11, '조리기구 세트', '프리미엄 논스틱 조리기구 세트.', 5, 'https://picsum.photos/200', 80, 120),
       (12, '청소기', '고효율 진공 청소기.', 5, 'https://picsum.photos/200', 30, 200),
       (13, '믹서기', '스무디를 만들기 좋은 강력한 믹서기.', 5, 'https://picsum.photos/200', 100, 99),
       (14, '무선 마우스', '인체공학적인 무선 마우스.', 1, 'https://picsum.photos/200', 150, 25),
       (15, '게이밍 의자', '편안한 게임용 의자.', 1, 'https://picsum.photos/200', 20, 250),
       (16, '판타지 소설', '스릴 넘치는 판타지 모험 이야기.', 2, 'https://picsum.photos/200', 120, 20),
       (17, '그래픽 노블', '화려한 일러스트가 돋보이는 그래픽 노블.', 2, 'https://picsum.photos/200', 80, 18),
       (18, '겨울 재킷', '따뜻하고 포근한 겨울 재킷.', 3, 'https://picsum.photos/200', 70, 120),
       (19, '봉제 인형', '귀엽고 부드러운 봉제 인형.', 4, 'https://picsum.photos/200', 200, 25),
       (20, '벽시계', '집에 어울리는 모던한 벽시계.', 5, 'https://picsum.photos/200', 90, 40);
INSERT INTO product_review (product_id, member_id, title, content, stars)
VALUES (6, 1, '가격 대비 좋음', '가격 대비 훌륭한 가치입니다. 다시 구매할 의향이 있습니다!', 3),
       (5, 1, '조금 아쉬움', '안타깝게도 이 제품은 기대에 미치지 못했습니다.', 4),
       (3, 1, '실망스러움', '이 제품을 정말 좋아했습니다. 강력 추천합니다!', 1),
       (4, 1, '그럭저럭', '그냥 괜찮았어요, 특별한 건 없었습니다.', 2),
       (7, 1, '조금 아쉬움', '품질이 다소 부족했지만 사용할 만했습니다.', 5);

INSERT INTO product_order (product_id, member_id, address, order_count, payment_status, delivery_status)
VALUES (7, 1, 'Mock Address 1', 4, 'COMPLETE', 'PREPARING'),
       (3, 1, 'Mock Address 2', 2, 'COMPLETE', 'DELIVER_COMPLETED'),
       (10, 1, 'Mock Address 3', 3, 'COMPLETE', 'PREPARING'),
       (1, 1, 'Mock Address 4', 5, 'COMPLETE', 'PREPARING'),
       (8, 1, 'Mock Address 5', 1, 'COMPLETE', 'DELIVER_COMPLETED'),
       (2, 1, 'Mock Address 6', 4, 'COMPLETE', 'PREPARING'),
       (5, 1, 'Mock Address 7', 2, 'COMPLETE', 'PREPARING'),
       (9, 1, 'Mock Address 8', 3, 'COMPLETE', 'DELIVER_COMPLETED'),
       (4, 1, 'Mock Address 9', 1, 'COMPLETE', 'DELIVER_COMPLETED'),
       (6, 1, 'Mock Address 10', 2, 'COMPLETE', 'DELIVER_COMPLETED'),
       (3, 1, 'Mock Address 11', 3, 'COMPLETE', 'PREPARING'),
       (8, 1, 'Mock Address 12', 5, 'COMPLETE', 'PREPARING'),
       (2, 1, 'Mock Address 13', 4, 'INCOMPLETE', 'PREPARING');

INSERT INTO wishlist (product_id, member_id)
VALUES (1, 1),
       (2, 1),
       (3, 1);

INSERT INTO cart (product_id, member_id)
VALUES (4, 1),
       (5, 1),
       (6, 1);