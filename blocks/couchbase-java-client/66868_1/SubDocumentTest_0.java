    private static class JacksonEntity {
        private String name;
        private int value;

        @JsonIgnore
        private String ignore;

        private JacksonEntity() {
            this.ignore = "zzz";
        }

        public JacksonEntity(String name, int value) {
            this.name = name;
            this.value = value;
            this.ignore = "zzz";
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            JacksonEntity that = (JacksonEntity) o;

            if (value != that.value) {
                return false;
            }
            return name != null ? name.equals(that.name) : that.name == null;

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + value;
            return result;
        }
    }

    @Test
    public void testRawGetWithMulti() throws IOException {
        JacksonEntity entity = new JacksonEntity("testRaw", 123);
        JsonObject expectedStore = JsonObject.create()
                .put("name", "testRaw")
                .put("value", 123);
        ObjectMapper mapper = new ObjectMapper();

        ctx.bucket()
                .mutateIn(key)
                .upsert("user", entity, false)
                .upsert("user2", entity, false)
                .remove("sub")
                .remove("array")
                .remove("string")
                .remove("boolean")
                .execute();

        final DocumentFragment<Lookup> fragment = ctx.bucket().async().lookupIn(key)
                .includeRaw(true)
                .get("user")
                .get("user2")
                .execute()
                .toBlocking().single();

        Object c1 = fragment.rawContent(0);
        Object c2 = fragment.rawContent(1);

        assertTrue(c1 instanceof byte[]);
        assertTrue(c2 instanceof byte[]);
        Assertions.assertThat(mapper.readValue((byte[]) c1, JacksonEntity.class)).isEqualTo(entity);
        Assertions.assertThat(mapper.readValue((byte[]) c2, JacksonEntity.class)).isEqualTo(entity);
        Assertions.assertThat(fragment.content(0)).isEqualTo(expectedStore);
        Assertions.assertThat(fragment.content(1)).isEqualTo(expectedStore);
    }

    @Test
    public void testRawGetSingle() throws IOException {
        JacksonEntity entity = new JacksonEntity("testRaw", 123);
        JsonObject expectedStore = JsonObject.create()
                .put("name", "testRaw")
                .put("value", 123);
        ObjectMapper mapper = new ObjectMapper();

        ctx.bucket()
                .mutateIn(key)
                .upsert("user", entity, false)
                .upsert("user2", entity, false)
                .remove("sub")
                .remove("array")
                .remove("string")
                .remove("boolean")
                .execute();

        final DocumentFragment<Lookup> fragment = ctx.bucket().async().lookupIn(key)
                .includeRaw(true)
                .get("user")
                .execute()
                .toBlocking().single();

        Object c1 = fragment.rawContent(0);

        assertTrue(c1 instanceof byte[]);
        Assertions.assertThat(mapper.readValue((byte[]) c1, JacksonEntity.class)).isEqualTo(entity);
        Assertions.assertThat(fragment.content(0)).isEqualTo(expectedStore);
    }

