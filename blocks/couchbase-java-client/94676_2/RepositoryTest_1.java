
    public static abstract class Parent {
         @Id
         private String id;

         Parent(String id) {
            this.id = id;
         }

        public String getId() {
            return id;
        }
    }

    public static class Child extends Parent {
        @Field
        private String name;

        public Child() {
            super(null);
        }

        public Child(String id, String name) {
            super(id);
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

