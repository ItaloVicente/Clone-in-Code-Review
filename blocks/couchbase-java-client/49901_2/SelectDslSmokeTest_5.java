    @Test
    public void test16() {
        Statement statement = select(x("fname").concat("\" \"").concat("lname").as("full_name"),
                x("email"), x("children[0:2]").as("offsprings"))
                .from("tutorial")
                .where(
                        x("email").like(s("%@yahoo.com"))
                                .or(x("ANY child IN tutorial.children SATISFIES child.age > 10 END")));
        assertEquals("SELECT fname || \" \" || lname AS full_name, email, children[0:2] AS offsprings " +
                "FROM tutorial WHERE email LIKE \"%@yahoo.com\" " +
                "OR ANY child IN tutorial.children SATISFIES child.age > 10 END",
                statement.toString());
    }

    @Test
    public void test17() {
        Statement statement = select("fname", "age").from("tutorial").orderBy(Sort.def("age"));
        assertEquals("SELECT fname, age FROM tutorial ORDER BY age", statement.toString());
    }

    @Test
    public void test18() {
        Statement statement = select("fname", "age")
                .from("tutorial")
                .orderBy(Sort.def("age")).limit(2);
        assertEquals("SELECT fname, age FROM tutorial ORDER BY age LIMIT 2", statement.toString());
    }

    @Test
    public void test19() {
        Statement statement = select(count("*").as("count"))
                .from("tutorial");
        assertEquals("SELECT COUNT(*) AS count FROM tutorial", statement.toString());
    }

    @Test
    public void test20() {
        Statement statement = select(x("relation"), count("*").as("count"))
                .from("tutorial").groupBy(x("relation"));
        assertEquals("SELECT relation, COUNT(*) AS count FROM tutorial GROUP BY relation", statement.toString());
    }

    @Test
    public void test21() {
        Statement statement = select(x("relation"), count("*").as("count"))
                .from("tutorial").groupBy(x("relation"))
                .having(count("*").gt(1));
        assertEquals("SELECT relation, COUNT(*) AS count FROM tutorial GROUP BY relation " +
                "HAVING COUNT(*) > 1", statement.toString());
    }

    @Test
    public void test22() {
        Statement statement = select(x("ARRAY child.fname FOR child IN tutorial.children END").as("children_names"))
                .from("tutorial").where(x("children").isNotNull());
        assertEquals("SELECT ARRAY child.fname FOR child IN tutorial.children END AS children_names " +
                "FROM tutorial WHERE children IS NOT NULL", statement.toString());
    }

    @Test
    public void test23() {
        Statement statement = select(x("t.relation"), count("*").as("count"), avg("c.age").as("avg_age"))
                .from("tutorial").as("t")
                .unnest("t.children").as("c")
                .where(x("c.age").gt(10))
                .groupBy(x("t.relation"))
                .having(count("*").gt(1))
                .orderBy(Sort.desc("avg_age"))
                .limit(1).offset(1);

        assertEquals("SELECT t.relation, COUNT(*) AS count, AVG(c.age) AS avg_age " +
                "FROM tutorial AS t " +
                "UNNEST t.children AS c " +
                "WHERE c.age > 10 " +
                "GROUP BY t.relation " +
                "HAVING COUNT(*) > 1 " +
                "ORDER BY avg_age DESC " +
                "LIMIT 1 OFFSET 1", statement.toString());
    }

    @Test
    public void test24() {
        Statement statement = select("usr.personal_details", "orders")
                .from("users_with_orders").as("usr")
                .useKeys("Elinor_33313792")
                .join("orders_with_users").as("orders")
                .onKeys(x("ARRAY s.order_id FOR s IN usr.shipped_order_history END"));
        assertEquals("SELECT usr.personal_details, orders " +
                "FROM users_with_orders AS usr " +
                "USE KEYS \"Elinor_33313792\" " +
                "JOIN orders_with_users AS orders " +
                "ON KEYS ARRAY s.order_id FOR s IN usr.shipped_order_history END",
                statement.toString());
    }

    @Test
    public void test25() {
        Statement statement = select("usr.personal_details", "orders")
                .from("users_with_orders").as("usr")
                .useKeys("Tamekia_13483660")
                .leftJoin("orders_with_users").as("orders")
                .onKeys(x("ARRAY s.order_id FOR s IN usr.shipped_order_history END"));
        assertEquals("SELECT usr.personal_details, orders " +
                "FROM users_with_orders AS usr " +
                "USE KEYS \"Tamekia_13483660\" " +
                "LEFT JOIN orders_with_users AS orders " +
                "ON KEYS ARRAY s.order_id FOR s IN usr.shipped_order_history END", statement.toString());
    }
