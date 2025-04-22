
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
                round(sum(x("product.unitPrice * items.count"))).as("totalSpent"))
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
    @Ignore
    public void test46() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test47() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test48() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test49() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test50() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test51() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test52() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test53() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }

    @Test
    @Ignore
    public void test54() {
        Statement statement = select("*");
        assertEquals("", statement.toString());
    }
