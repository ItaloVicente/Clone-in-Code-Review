    static class Credential {
        public String username;
        public String password;

        public Credential(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String username() {
            return this.username;
        }

        public String password() {
            return this.password;
        }
    }

