    private static final class ShutdownStatus {
        public final String target;
        public final boolean success;
        public final Throwable cause;

        public ShutdownStatus(String target, boolean success, Throwable cause) {
            this.target = target;
            this.success = success;
            this.cause = cause;
        }

        @Override
        public String toString() {
            return "Shutdown " + target + ": " + (success ? "success " : "failure ") + (cause == null ? "" : " due to "
                    + cause.toString());
        }
    }
