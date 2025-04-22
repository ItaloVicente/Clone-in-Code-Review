    private static boolean checkIsPresent(final String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    private static final boolean OPENTRACING_PRESENT = checkIsPresent("io.opentracing.Span");
