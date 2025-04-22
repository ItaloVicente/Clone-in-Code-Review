    @Test
    public void shouldUpsertLegacyObjectDocument() {
        String id = "legacy-upsert";
        User user = new User("Michael");
        LegacyDocument doc = LegacyDocument.create(id, user);
        LegacyDocument stored = bucket().upsert(doc).toBlocking().single();
        assertTrue(stored.status().isSuccess());

        LegacyDocument found = bucket().get(id, LegacyDocument.class).toBlocking().single();
        assertEquals(found.content().getClass(), user.getClass());
        assertEquals("Michael", ((User) found.content()).getFirstname());
    }

    static class User implements Serializable {

        private final String firstname;

        User(String firstname) {
            this.firstname = firstname;
        }

        public String getFirstname() {
            return firstname;
        }
