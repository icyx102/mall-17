
DROP TABLE IF EXISTS ORDER_ITEM;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS PRODUCT_TAG;
DROP TABLE IF EXISTS TAG;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS CLASSIFICATION;
DROP TABLE IF EXISTS ADMIN_USER;
DROP TABLE IF EXISTS USERS;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
CREATE TABLE ADMIN_USER
(
    id       INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    password VARCHAR(64) NOT NULL COMMENT '密码',
    username VARCHAR(32) NOT NULL COMMENT '用户名'
);
-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO ADMIN_USER
VALUES (1, 'admin', 'admin');
INSERT INTO ADMIN_USER
VALUES (2, 'hfb', 'hfb');

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE USERS
(
    id       INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    addr     varchar(128) NULL     DEFAULT NULL COMMENT '地址',
    email    varchar(64)  NULL     DEFAULT NULL COMMENT '邮箱',
    name     varchar(32)  NOT NULL COMMENT '姓名',
    password varchar(64)  NOT NULL DEFAULT '123456' COMMENT '登录密码',
    phone    varchar(20)  NULL     DEFAULT NULL COMMENT '手机号码',
    username varchar(16)  NOT NULL COMMENT '用户名'
);

INSERT INTO USERS
VALUES (1, 'wew6698888', '89****96@qq.com', 'skywalker', '123456', '17688970006', 'hfb');
INSERT INTO USERS
VALUES (2, '1235645645646', '89***96@qq.com', 'hfb', '123456', '18645954845', 'jesse');
INSERT INTO USERS
VALUES (3, 'Pecs', '8976677657@qq.com', 'tomd', '123456', '12345678941', 'ztt');

-- ----------------------------
-- Table structure for classification
-- ----------------------------
CREATE TABLE CLASSIFICATION
(
    id        INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    cname     VARCHAR(32) NOT NULL COMMENT '名称',
    parent_id int         NULL DEFAULT NULL COMMENT '上一级类型ID',
    type      int              DEFAULT 1 COMMENT '类型',
    FOREIGN KEY (parent_id) REFERENCES CLASSIFICATION (id)
);

-- ----------------------------
-- Records of classification
-- ----------------------------
INSERT INTO CLASSIFICATION
VALUES (1, 'Clothing', NULL, 1);
INSERT INTO CLASSIFICATION
VALUES (2, 'PC', NULL, 1);
INSERT INTO CLASSIFICATION
VALUES (3, 'Phone', NULL, 1);
INSERT INTO CLASSIFICATION
VALUES (4, 'Food', NULL, 1);
INSERT INTO CLASSIFICATION
VALUES (5, 'Laptop', 2, 2);
INSERT INTO CLASSIFICATION
VALUES (6, 'Tablet', 2, 2);
INSERT INTO CLASSIFICATION
VALUES (7, 'PC', 2, 2);
INSERT INTO CLASSIFICATION
VALUES (9, 'Home', NULL, 1);
INSERT INTO CLASSIFICATION
VALUES (10, 'Drink', 4, 2);
INSERT INTO CLASSIFICATION
VALUES (11, 'Phone', 3, 2);


CREATE TABLE PRODUCT
(
    id           INT PRIMARY KEY AUTO_INCREMENT COMMENT '商品主键ID',
    title        VARCHAR(255) NULL COMMENT '标题',
    csid         int          NULL COMMENT '商品类型',
    is_hot       INT          NOT NULL DEFAULT 0 COMMENT '是否热售商品',
    market_price double COMMENT '市场价',
    shop_price   double COMMENT '商城价',
    image        VARCHAR(255) NULL COMMENT '商品图片地址',
    description  VARCHAR(512) NULL COMMENT '商品描述',
    pdate        datetime COMMENT '创建时间',
    FOREIGN KEY (csid) REFERENCES CLASSIFICATION (id)
);
INSERT INTO PRODUCT(title,csid,is_hot,market_price,shop_price,image,description,pdate)
VALUES
    ('Matebook D15 2024款',7,1,4500,4500,'4BE4F4E8C3CF70D908C689F255DDA1.jpg','华为Matebook D15笔记本，性能卓越','2024-02-14 12:00:00'),
    ('iPhone 15 Pro',11,1,6999,6999,'69F67AD25482F1DE1A2BF57699B7A6.jpg','iPhone 15 Pro是苹果公司推出的一款新型手机，搭载A17 Pro处理器，支持USB Type-C接口，并配备了4800万像素主摄像头和1200万像素超广角摄像头。UI设计上延续了灵动岛风格，其边框较前一代Pro的边框更窄，仅1.55毫米。这是苹果手机首次采用钛金属边框，成为有史以来比较轻的iPhone Pro系列手机。 此外，iPhone 15 Pro系列还支持快充，充电速率可达27W','2023-09-13 12:00:00'),
    ('HUAWEI Mate 40 Pro',11,1,5999,5999,'33E938B4556135EEC0C1F1AEF6F4DC.jpg','HUAWEI Mate 40 Pro搭载的是麒麟9000 5G SoC，由5纳米工艺制造，集成了5G调制解调器；搭载了1200万像素潜望式长焦摄像头支持5倍光学变焦、10倍混合变焦⁠和50倍数字变焦；支持66W华为有线超级快充和50W无线超级快充，30分钟即可充电79%，从关机到100%充满共需要48分钟','2020-10-22 12:00:00');


CREATE TABLE TAG(
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '标签主键ID',
    title VARCHAR(16) NOT NULL COMMENT '标签名称',
    create_time DATETIME NOT NULL  COMMENT '创建时间'
);

INSERT INTO TAG(id,title,create_time)VALUES
 (1,'A','2024-05-20 12:30:00'),
 (2,'B','2024-05-20 12:30:00'),
 (3,'C','2024-05-20 12:30:00'),
 (4,'D','2024-05-20 12:30:00');

CREATE TABLE PRODUCT_TAG(
    product_id INT NOT NULL COMMENT '产品ID',
    tag_id INT NOT NULL COMMENT '标签ID',
    PRIMARY KEY (product_id,tag_id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id),
    FOREIGN KEY (tag_id) REFERENCES TAG(id)
);
INSERT INTO PRODUCT_TAG(product_id,tag_id)VALUES
(1,1),
(1,2),
(2,2),
(2,3),
(3,4);


-- ----------------------------
-- Table structure for order
-- ----------------------------
CREATE TABLE ORDERS
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    addr       varchar(255) NULL DEFAULT NULL,
    name       varchar(255) NULL DEFAULT NULL,
    order_time datetime     NULL DEFAULT NULL,
    phone      varchar(255) NULL DEFAULT NULL,
    state      int          NULL DEFAULT NULL,
    total      double       NULL DEFAULT NULL,
    user_id    int          NULL DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id)
);

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO ORDERS
VALUES (1, '广东省深圳市福田区', '华为笔记本电脑Matebook D15 2024款', '2024-11-25 19:23:48', '12345654', 4, 4500, 1);
INSERT INTO ORDERS
VALUES (2, '湖南省长沙市芙蓉区', '苹果手机iPhone 15 Pro', '2024-11-25 22:10:39', '123456894', 2, 21000, 1);
INSERT INTO ORDERS
VALUES (3, '湖北省武汉市汉口', '华为手机Mate 40 Pro', '2024-11-25 22:52:44', '1234322313', 3, 18000, 1);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
CREATE TABLE ORDER_ITEM
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    count      int    NULL DEFAULT NULL,
    order_id   int    NULL DEFAULT NULL,
    product_id int    NULL DEFAULT NULL,
    sub_total  double NULL DEFAULT NULL,
    FOREIGN KEY (order_id) REFERENCES ORDERS (id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id)
);

INSERT INTO ORDER_ITEM
VALUES (1, 1, 1, 1, 4500);
INSERT INTO ORDER_ITEM
VALUES (2, 2, 2, 2, 21000);
INSERT INTO ORDER_ITEM
VALUES (3, 3, 3, 3, 18000);


INSERT INTO PRODUCT(description,csid,image,is_hot,market_price,pdate,shop_price,title)VALUES
('新一代 Surface Pro 比以往更出色，它不仅仅是一台笔记本，还能在工作室模式和平板间灵活切换.\r\n\r\n随心所欲，百变菁英 震撼的 PixelSense™ 显示屏支持触控笔* 和触摸操作，设计方面也有所改进，与 Surface Pro 4 相比，速度和性能都得到了进一步提升，电池续航能力有较大提高。\r\n\r\n无风扇，更安静 灵感随意 随手拈来 \r\n\r\n快捷刷脸登陆 保护隐私 轻松唤醒刷脸登陆 充分保护您的私人数据**** 无论您喜欢摄影、绘画、音乐或创作\r\n\r\n精彩视频\r\nSurface Pro总能满足您诸多创作需求 Surface Pro 酷睿 m3 和 i5 型号配备全新无风扇冷却系统***，\r\ni7 型号改进了混合冷却系统，您可以更安静地工作或播放喜欢的节目.',5,'/mall/admin/product/img/6AAB47CEA20BD01DE5EE21A19B3316.jpg',1,9999,'2023-06-10 11:52:53',8999,'微软（Microsoft）新Surface Pro 二合一平板电脑 12.3英寸（Intel Core i5 8G内存 256G存储 ）'),
('一直以来，我们都心存一个设想，期待着能够打造出这样一部 iPhone：它有整面的屏幕，能让你在使用时完全沉浸其中，仿佛忘记了它的存在。它是如此智能，你的一触、一碰、一言、一语，哪怕是轻轻一瞥，都会得到它心有灵犀的回应。而这个设想，终于随着 iPhone X 的到来成为了现实。现在，就跟未来见个面吧。',11,'/mall/admin/product/img/33E938B4556135EEC0C1F1AEF6F4DC.jpg',1,9999,'2023-06-10 11:53:00',8888,'Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机'),
('京东价：京东价为商品的销售价，是您最终决定是否购买商品的依据。\r\n划线价：商品展示的划横线价格为参考价，该价格可能是品牌专柜标价、商品吊牌价或由品牌供应商提供的正品零售价（如厂商指导价、建议零售价等）或该商品在京东平台上曾经展示过的销售价；由于地区、时间的差异性和市场行情波动，品牌专柜标价、商品吊牌价等可能会与您购物时展示的不一致，该价格仅供您参考。\r\n折扣：如无特殊说明，折扣指销售商在原价、或划线价（如品牌专柜标价、商品吊牌价、厂商指导价、厂商建议零售价）等某一价格基础上计算出的优惠比例或优惠金额；如有疑问，您可在购买前联系销售商进行咨询。\r\n异常问题：商品促销信息以商品详情页“促销”栏中的信息为准；商品的具体售价以订单结算页价格为准；如您发现活动商品售价或促销信息有异常，建议购买前先联系销售商咨询.',10,'/mall/admin/product/img/0DBF963BD401EF5F704110D7423DCC.jpg',0,40.9,'2023-06-10 11:53:05',39,'可口可乐330ml*24听整箱装'),
('搭载NVIDIA GeForce GTX1060 3G独立显卡，强大的图像显示和处理功能，\r\n既可以高画质下流畅运行工作软件，也支持主流大型游戏，工作游戏，左右兼顾。\r\n并支持兼容主流VR眼镜设备，为你带来身临其境的沉浸体验。',7,'/mall/admin/product/img/11B43B8526F655BF72AA0834F28796.jpg',1,4999,'2023-06-10 11:53:11',3999,'惠普（HP）光影精灵580 吃鸡游戏主机（i5-7400 8G 128GSSD+1T GTX1060）'),
('原本就拥有强劲的基础性能，能够轻松通吃时下的主流电竞游戏；外观方面整机采用多面切割搭配碳纤铠甲风格，搭配“胜利之眼”游戏氛围灯，凸显电竞元素；最主要的是这是一款UIY电竞主机，机箱内部已经给升级留足了接口和空间，在官方配置的基础上我们还可以进行性能和外观方面的额升级，而且官方配件仍然能在保修范围内。品牌PC厂商参与到PC个性化定制和部件升级服务中来，同时提供品牌厂商一贯的服务优势，完全解决了DIY模式下遇到的种种痛点。不得不说联想拯救者刃 7000的出现，开启了PC UIY时代。',7,'/mall/admin/product/img/4BE4F4E8C3CF70D908C689F255DDA1.jpg',1,6499,'2023-06-10 11:53:18',5999,'联想（Lenovo）拯救者刃7000 UIY主机( i7-7700 8G 128G SSD 1T硬盘 GTX1060 Win10)');