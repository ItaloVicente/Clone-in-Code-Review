
        statement = new DefaultFromPath(null)
                .from("users_with_orders").as("user")
                .join("orders_with_users").as("orders")
                .onKeys(JsonArray.from("key1", "key2"));
        assertEquals("FROM users_with_orders AS user JOIN orders_with_users AS orders ON KEYS [\"key1\",\"key2\"]",
                statement.toString());

        statement = new DefaultFromPath(null)
                .from("users_with_orders").as("user")
                .join("orders_with_users").as("orders")
                .onKeys("orders.id");
        assertEquals("FROM users_with_orders AS user JOIN orders_with_users AS orders ON KEYS orders.id",
                statement.toString());
