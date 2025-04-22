        JsonDocument storedRaw = bucket().get(entity.id());
        assertEquals(entity.name(), storedRaw.content().getString("name"));
        assertEquals(entity.published(), storedRaw.content().getBoolean("published"));
        assertEquals(entity.someNumber(), storedRaw.content().getInt("num"), 0);
        assertEquals(entity.otherNumber(), storedRaw.content().getDouble("otherNumber"), 0);

        EntityDocument<User> found = repository().get(entity.id(), User.class);
        assertEquals(found.cas(), stored.cas());
        assertEquals(entity, found.content());
        assertNotEquals(0, found.cas());

        assertTrue(repository().exists(document));
    }

    @Test
    public void shouldInsertEntity() {
        User entity = new User("Tom", false, -34, -55.6766);
        EntityDocument<User> document = EntityDocument.create(entity.id(), entity);

        assertFalse(repository().exists(document));
        EntityDocument<User> stored = repository().insert(document);
        assertEquals(document.content(), stored.content());

        JsonDocument storedRaw = bucket().get(entity.id());
        assertEquals(entity.name(), storedRaw.content().getString("name"));
        assertEquals(entity.published(), storedRaw.content().getBoolean("published"));
        assertEquals(entity.someNumber(), storedRaw.content().getInt("num"), 0);
        assertEquals(entity.otherNumber(), storedRaw.content().getDouble("otherNumber"), 0);

        EntityDocument<User> found = repository().get(entity.id(), User.class);
        assertEquals(entity, found.content());
        assertNotEquals(0, found.cas());

        assertTrue(repository().exists(document));
    }

    @Test
    public void shouldReplaceEntity() {
        User entity = new User("John", false, -34, -55.6766);
        EntityDocument<User> document = EntityDocument.create(entity.id(), entity);

        assertFalse(repository().exists(document));
        EntityDocument<User> stored = repository().upsert(document);
        assertEquals(document.content(), stored.content());

        entity = new User("John", true, 0, 0);
        document = EntityDocument.create(entity.id(), entity);

        EntityDocument<User> replaced = repository().replace(document);
        assertEquals(entity, replaced.content());

        JsonDocument storedRaw = bucket().get(entity.id());
        assertEquals(entity.name(), storedRaw.content().getString("name"));
        assertEquals(entity.published(), storedRaw.content().getBoolean("published"));
        assertEquals(entity.someNumber(), storedRaw.content().getInt("num"), 0);
        assertEquals(entity.otherNumber(), storedRaw.content().getDouble("otherNumber"), 0);

        EntityDocument<User> found = repository().get(entity.id(), User.class);
        assertEquals(entity, found.content());
        assertNotEquals(0, found.cas());

        assertTrue(repository().exists(document));
    }

    @Test
    public void shouldRemoveEntity() {
        User entity = new User("Jane", false, -34, -55.6766);
        EntityDocument<User> document = EntityDocument.create(entity.id(), entity);

        assertFalse(repository().exists(document));
        EntityDocument<User> stored = repository().upsert(document);
        assertEquals(entity, stored.content());
        assertTrue(repository().exists(stored));

        EntityDocument<User> removed = repository().remove(stored);
        assertFalse(repository().exists(removed));
        assertEquals(document.id(), removed.id());
