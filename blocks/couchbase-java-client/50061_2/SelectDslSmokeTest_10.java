
    @Test
    public void test26() {
        Statement statement = select("usr.personal_details", "orders")
                .from("users_with_orders").as("usr")
                .useKeysValues("Elinor_33313792")
                .nest("orders_with_users").as("orders")
                .onKeys(arrayIn(x("s.order_id"), "s", x("usr.shipped_order_history")).end());

        assertEquals("SELECT usr.personal_details, orders " +
                "FROM users_with_orders AS usr " +
                "USE KEYS \"Elinor_33313792\" " +
                "NEST orders_with_users AS orders " +
                "ON KEYS ARRAY s.order_id FOR s IN usr.shipped_order_history END", statement.toString());
    }

    @Test
    public void test27() {
        Statement statement = select("*").from("tutorial").as("contact")
                .unnest("contact.children").where(x("contact.fname").eq(s("Dave")));

        assertEquals("SELECT * FROM tutorial AS contact UNNEST contact.children " +
                "WHERE contact.fname = \"Dave\"", statement.toString());
    }


    @Test
    public void test28() {
        Statement statement = select(x("u.personal_details.display_name").as("name"),
                x("s").as("order_no"), x("o.product_details"))
                .from("users_with_orders").as("u")
                .useKeys(s("Aide_48687583"))
                .unnest("u.shipped_order_history").as("s")
                .join("users_with_orders").as("o").onKeys("s.order_id");

        assertEquals("SELECT u.personal_details.display_name AS name, s AS order_no, o.product_details " +
                "FROM users_with_orders AS u USE KEYS \"Aide_48687583\" " +
                "UNNEST u.shipped_order_history AS s " +
                "JOIN users_with_orders AS o ON KEYS s.order_id", statement.toString());
    }

    @Test
    @Ignore("needs EXPLAIN and INSERT dsl")
    public void test29() {
        Statement statement = select("TODO");

        assertEquals("EXPLAIN INSERT INTO tutorial (KEY, VALUE) " +
                "VALUES (\"baldwin\", {\"name\":\"Alex Baldwin\", \"type\":\"contact\"})", statement.toString());
    }

    @Test
    @Ignore("need EXPLAIN and DELETE dsl")
    public void test30() {
        Statement statement = select("*");

        assertEquals("EXPLAIN DELETE FROM tutorial t USE KEYS \"baldwin\" RETURNING t", statement.toString());
    }

    @Test
    @Ignore("needs EXPLAIN and UPDATE dsl")
    public void test31() {
        Statement statement = select("*");

        assertEquals("EXPLAIN UPDATE tutorial USE KEYS \"baldwin\" " +
                "SET type = \"actor\" RETURNING tutorial.type", statement.toString());
    }

    @Test
    public void test32() {
        Statement statement = select(count("*").as("product_count")).from("product");
        assertEquals("SELECT COUNT(*) AS product_count FROM product", statement.toString());
    }

    @Test
    public void test33() {
        Statement statement = select("*").from("product")
                .unnest("product.categories").as("cat")
                .where(lower("cat").in(JsonArray.from("golf")))
                .limit(10).offset(10);

        assertEquals("SELECT * FROM product " +
                "UNNEST product.categories AS cat " +
                "WHERE LOWER(cat) IN [\"golf\"] LIMIT 10 OFFSET 10", statement.toString());
    }

    @Test
    public void test34() {
        Statement statement = selectDistinct("categories").from("product")
                .unnest("product.categories").as("categories");

        assertEquals("SELECT DISTINCT categories FROM product " +
                "UNNEST product.categories AS categories", statement.toString());
    }

    @Test
    public void test35() {
        Statement statement = select("productId", "name").from("product")
                .where(lower("name").like(s("%cup%")));

        assertEquals("SELECT productId, name FROM product WHERE LOWER(name) LIKE \"%cup%\"", statement.toString());
    }

    @Test
    public void test36() {
        Statement statement = select("product").from("product")
                .unnest("product.categories").as("categories")
                .where(x("categories").eq(s("Appliances")));

        assertEquals("SELECT product FROM product UNNEST product.categories AS categories " +
                "WHERE categories = \"Appliances\"", statement.toString());
    }

    @Test
    public void test37() {
        Statement statement = select(
                x("product.name"),
                count("reviews").as("reviewCount"),
                round(avg("reviews.rating"), 1).as("AvgRating"),
                x("category"))
                .from("reviews").as("reviews")
                .join("product").as("product").onKeys("reviews.productId");

        assertEquals("SELECT product.name, COUNT(reviews) AS reviewCount, " +
                "ROUND(AVG(reviews.rating), 1) AS AvgRating, category " +
                "FROM reviews AS reviews " +
                "JOIN product AS product ON KEYS reviews.productId", statement.toString());
    }

    @Test
    public void test38() {
        Statement statement = select(x("product.name"), x("product.dateAdded"), sum("items.count").as("unitsSold"))
                .from("purchases").unnest("purchases.lineItems").as("items")
                .join("product").onKeys("items.product").groupBy("product")
                .orderBy(Sort.def("product.dateAdded"), Sort.desc("unitsSold"))
                .limit(10);

        assertEquals("SELECT product.name, product.dateAdded, SUM(items.count) AS unitsSold " +
                "FROM purchases UNNEST purchases.lineItems AS items " +
                "JOIN product ON KEYS items.product GROUP BY product " +
                "ORDER BY product.dateAdded, unitsSold DESC LIMIT 10", statement.toString());
    }

    @Test
    public void test39() {
        Statement statement = select("product.name", "product.unitPrice", "product.categories").from("product")
                .unnest("product.categories").as("categories")
                .where(x("categories").eq(s("Appliances"))
                                      .and(x("product.unitPrice").lt(6.99)));

        assertEquals("SELECT product.name, product.unitPrice, product.categories FROM product " +
                "UNNEST product.categories AS categories WHERE categories = \"Appliances\" AND product.unitPrice < 6.99",
                statement.toString());
    }

    @Test
    public void test40() {
        Statement statement = select(x("product.name"), sum("items.count").as("unitsSold")).from("purchases")
                .unnest("purchases.lineItems").as("items")
                .join("product").onKeys("items.product")
                .groupBy("product")
                .orderBy(Sort.desc("unitsSold")).limit(10);

        assertEquals("SELECT product.name, SUM(items.count) AS unitsSold FROM purchases " +
                "UNNEST purchases.lineItems AS items JOIN product ON KEYS items.product " +
                "GROUP BY product ORDER BY unitsSold DESC LIMIT 10", statement.toString());
    }

    @Test
    public void test41() {
        Statement statement = select(x("product.name"), round(avg("reviews.rating"), 1).as("avg_rating"))
                .from("reviews")
                .join("product").onKeys("reviews.productId")
                .groupBy("product")
                .orderBy(Sort.desc(avg("reviews.rating"))).limit(5);

        assertEquals("SELECT product.name, ROUND(AVG(reviews.rating), 1) AS avg_rating FROM reviews " +
                "JOIN product ON KEYS reviews.productId GROUP BY product " +
                "ORDER BY AVG(reviews.rating) DESC LIMIT 5", statement.toString());
    }

    @Test
    public void test42() {
        Statement statement = select("purchases", "product", "customer").from("purchases")
                .useKeysValues("purchase0")
                .unnest("purchases.lineItems").as("items")
                .join("product").onKeys("items.product")
                .join("customer").onKeys("purchases.customerId");

        assertEquals("SELECT purchases, product, customer FROM purchases USE KEYS \"purchase0\" " +
                "UNNEST purchases.lineItems AS items JOIN product ON KEYS items.product " +
                "JOIN customer ON KEYS purchases.customerId", statement.toString());
    }

    @Test
    public void test43() {
        Statement statement = select(x("customer.firstName"), x("customer.lastName"), x("customer.emailAddress"),
                sum("items.count").as("purchaseCount"),
                round(sum(x("product.unitPrice").multiply("items.count"))).as("totalSpent"))
                .from("purchases")
                .unnest("purchases.lineItems").as("items")
                .join("product").onKeys("items.product")
                .join("customer").onKeys("purchases.customerId")
                .groupBy("customer");

        assertEquals("SELECT customer.firstName, customer.lastName, customer.emailAddress, " +
                "SUM(items.count) AS purchaseCount, ROUND(SUM(product.unitPrice * items.count)) AS totalSpent " +
                "FROM purchases UNNEST purchases.lineItems AS items " +
                "JOIN product ON KEYS items.product JOIN customer ON KEYS purchases.customerId GROUP BY customer",
                statement.toString());
    }

    @Test
    public void test44() {
        Statement statement = select(count("customer").as("customerCount"), x("state")).from("customer")
                .groupBy("state").orderBy(Sort.desc("customerCount"));

        assertEquals("SELECT COUNT(customer) AS customerCount, state FROM customer " +
                "GROUP BY state ORDER BY customerCount DESC", statement.toString());
    }

    @Test
    public void test45() {
        Statement statement = select(count(distinct("purchases.customerId"))).from("purchases")
                .where(strToMillis("purchases.purchasedAt")
                        .between(strToMillis(s("2014-02-01"))
                                .and(strToMillis(s("2014-03-01")))));

        assertEquals("SELECT COUNT(DISTINCT purchases.customerId) FROM purchases " +
                "WHERE STR_TO_MILLIS(purchases.purchasedAt) BETWEEN STR_TO_MILLIS(\"2014-02-01\") " +
                "AND STR_TO_MILLIS(\"2014-03-01\")", statement.toString());
    }

    @Test
    public void test46() {
        Statement statement = select(x("product"), avg("reviews.rating").as("avgRating"),
                count("reviews").as("numReviews"))
                .from("product")
                .join("reviews").onKeys("product.reviewList")
                .groupBy("product").having(avg("reviews.rating").lt(1));

        assertEquals("SELECT product, AVG(reviews.rating) AS avgRating, COUNT(reviews) AS numReviews " +
                "FROM product JOIN reviews ON KEYS product.reviewList " +
                "GROUP BY product HAVING AVG(reviews.rating) < 1", statement.toString());
    }

    @Test
    public void test47() {
        Statement statement = select(substr("purchases.purchasedAt", 0, 7).as("month"),
                round(sum(x("product.unitPrice").multiply("items.count")).divide(1000000), 3).as("revenueMillion"))
                .from("purchases")
                .unnest("purchases.lineItems").as("items")
                .join("product").onKeys("items.product")
                .groupBy(substr("purchases.purchasedAt", 0, 7))
                .orderBy(Sort.def("month"));

        assertEquals("SELECT SUBSTR(purchases.purchasedAt, 0, 7) AS month, " +
                "ROUND(SUM(product.unitPrice * items.count) / 1000000, 3) AS revenueMillion " +
                "FROM purchases UNNEST purchases.lineItems AS items JOIN product ON KEYS items.product " +
                "GROUP BY SUBSTR(purchases.purchasedAt, 0, 7) " +
                "ORDER BY month", statement.toString());
    }

    @Test
    public void test48() {
        Statement statement = select("purchases.purchaseId", "l.product").from("purchases")
                .unnest("purchases.lineItems").as("l")
                .where(datePartStr("purchases.purchasedAt", "month").eq(4)
                .and(datePartStr("purchases.purchasedAt", "year").eq(2014))
                .and(sub(select("product.productId")
                                .from("product")
                                .useKeys("l.product")
                                .where(x("product.unitPrice").gt(500)))
                    .exists()
                ));

        assertEquals("SELECT purchases.purchaseId, l.product FROM purchases UNNEST purchases.lineItems AS l " +
                "WHERE DATE_PART_STR(purchases.purchasedAt,\"month\") = 4 " +
                "AND DATE_PART_STR(purchases.purchasedAt,\"year\") = 2014 " +
                "AND EXISTS (SELECT product.productId " +
                "FROM product USE KEYS l.product WHERE product.unitPrice > 500)", statement.toString());
    }

    @Test
    public void test49() {
        Statement statement = select("*").from("jungleville_inbox").limit(1);
        assertEquals("SELECT * FROM jungleville_inbox LIMIT 1", statement.toString());
    }

    @Test
    public void test50() {
        Statement statement = select("*").from("jungleville").as("`game-data`")
                .join("jungleville_stats").as("stats").onKeysValues("zid-jungle-stats-0001")
                .nest("jungleville_inbox").as("inbox").onKeysValues("zid-jungle-inbox-0001")
                .where(path(i("game-data"), "uuid").eq(s("zid-jungle-0001")));

        assertEquals("SELECT * FROM jungleville AS `game-data` JOIN jungleville_stats AS stats " +
                "ON KEYS \"zid-jungle-stats-0001\" NEST jungleville_inbox AS inbox ON KEYS \"zid-jungle-inbox-0001\" " +
                "WHERE `game-data`.uuid = \"zid-jungle-0001\"", statement.toString());
    }

    @Test
    public void test51() {
        Statement statement = select("player.name", "inbox.messages")
                .from("jungleville").as("player")
                .useKeysValues("zid-jungle-0001")
                .leftJoin("jungleville_inbox").as("inbox")
                .onKeys(s("zid-jungle-inbox-").concat(substr("player.uuid", 11)));

        assertEquals("SELECT player.name, inbox.messages FROM jungleville AS player USE KEYS \"zid-jungle-0001\" " +
                "LEFT JOIN jungleville_inbox AS inbox ON KEYS \"zid-jungle-inbox-\" || SUBSTR(player.uuid, 11)", statement.toString());
    }

    @Test
    public void test52() {
        Statement statement = select(x("stats.uuid").as("player"), x("hist.uuid").as("opponent"),
                sum(caseSearch(x("hist.result").eq(s("won")), x(1)).elseEnd(x(0))).as("wins"),
                sum(caseSearch(x("hist.result").eq(s("lost")), x(1)).elseEnd(x(0))).as("losses"))
                .from("jungleville_stats").as("stats").useKeysValues("zid-jungle-stats-0004")
                .unnest("stats.`pvp-hist`").as("hist")
                .groupBy("stats.uuid", "hist.uuid");

        assertEquals("SELECT stats.uuid AS player, hist.uuid AS opponent, " +
                "SUM(CASE WHEN hist.result = \"won\" THEN 1 ELSE 0 END) AS wins, " +
                "SUM(CASE WHEN hist.result = \"lost\" THEN 1 ELSE 0 END) AS losses " +
                "FROM jungleville_stats AS stats USE KEYS \"zid-jungle-stats-0004\" " +
                "UNNEST stats.`pvp-hist` AS hist GROUP BY stats.uuid, hist.uuid", statement.toString());
    }

    @Test
    public void test53() {
        Statement statement = select(x("player.name"), x("player.level"), x("stats.loadtime"),
                sum(caseSearch(x("hist.result").eq(s("won")), x(1)).elseEnd(x(0))).as("wins"))
                .from("jungleville_stats").as("stats")
                .unnest("stats.`pvp-hist`").as("hist")
                .join("jungleville").as("player")
                .onKeys("stats.uuid")
                .groupBy("player", "stats");

        assertEquals("SELECT player.name, player.level, stats.loadtime, " +
                "SUM(CASE WHEN hist.result = \"won\" THEN 1 ELSE 0 END) AS wins " +
                "FROM jungleville_stats AS stats UNNEST stats.`pvp-hist` AS hist " +
                "JOIN jungleville AS player ON KEYS stats.uuid GROUP BY player, stats", statement.toString());
    }

    @Test
    public void test54() {
        Statement statement = select("jungleville.level", "friends")
                .from("jungleville").useKeysValues("zid-jungle-0002")
                .join("jungleville.friends").onKeys("jungleville.friends");

        assertEquals("SELECT jungleville.level, friends FROM jungleville USE KEYS \"zid-jungle-0002\" " +
                "JOIN jungleville.friends ON KEYS jungleville.friends", statement.toString());
    }
