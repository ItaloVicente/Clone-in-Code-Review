
        assertTrue(repository().exists(user));
    }

    @Test
    public void shouldInsertEntity() {
        User user = new User("Tom", false, -34, -55.6766);
        assertFalse(repository().exists(user));
        User stored = repository().upsert(user);
        assertEquals(user, stored);

        JsonDocument storedRaw = bucket().get(user.id());
        assertEquals(user.name(), storedRaw.content().getString("name"));
        assertEquals(user.published(), storedRaw.content().getBoolean("published"));
        assertEquals(user.someNumber(), storedRaw.content().getInt("num"), 0);
        assertEquals(user.otherNumber(), storedRaw.content().getDouble("otherNumber"), 0);

        User found = repository().get(user.id(), User.class);
        assertEquals(user, found);

        assertTrue(repository().exists(user));
    }

    @Test
    public void shouldReplaceEntity() {
        User user = new User("John", false, -34, -55.6766);
        assertFalse(repository().exists(user));
        User stored = repository().upsert(user);
        assertEquals(user, stored);
        assertTrue(repository().exists(user));

        user = new User("John", true, 0, 0);
        User replaced = repository().replace(user);
        assertEquals(user, replaced);

        JsonDocument storedRaw = bucket().get(user.id());
        assertEquals(user.name(), storedRaw.content().getString("name"));
        assertEquals(user.published(), storedRaw.content().getBoolean("published"));
        assertEquals(user.someNumber(), storedRaw.content().getInt("num"), 0);
        assertEquals(user.otherNumber(), storedRaw.content().getDouble("otherNumber"), 0);
    }

    @Test
    public void shouldRemoveEntity() {
        User user = new User("Jane", false, -34, -55.6766);
        assertFalse(repository().exists(user));
        User stored = repository().upsert(user);
        assertEquals(user, stored);
        assertTrue(repository().exists(user));

        User removed = repository().remove(user);
        assertFalse(repository().exists(user));
        assertEquals(user.id(), removed.id());
