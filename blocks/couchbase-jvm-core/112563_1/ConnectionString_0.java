
    public static class UnresolvedSocket {
        private final String hostname;
        private final int port;

        UnresolvedSocket(String hostname, int port) {
            this.hostname = hostname;
            this.port = port;
        }

        public String hostname() {
            return hostname;
        }

        public int port() {
            return port;
        }

        @Override
        public String toString() {
            return "UnresolvedSocket{" +
              "hostname='" + hostname + '\'' +
              ", port=" + port +
              '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UnresolvedSocket that = (UnresolvedSocket) o;
            return port == that.port &&
              Objects.equals(hostname, that.hostname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hostname, port);
        }
    }
