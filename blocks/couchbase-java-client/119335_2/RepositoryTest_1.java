    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    @Id
    public @interface MetaId {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    @Field
    public @interface MetaField {}

    static class MetaEntity {
        public @MetaId
        String id = null;

        @MetaField
        private String name = null;

        public MetaEntity() {
        }

        public MetaEntity(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

