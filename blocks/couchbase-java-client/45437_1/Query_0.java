
    private static class RawStatement implements Statement {
        private final String rawStatement;

        public RawStatement(String rawStatement) {
            this.rawStatement = rawStatement;
        }

        @Override
        public String toString() {
            return rawStatement;
        }
    }

