    private static final EOFException NEED_MORE_DATA = new EOFException();
    static {
        NEED_MORE_DATA.setStackTrace(new StackTraceElement[0]);
    }

    private final JsonPointerTree tree;
    private final Stack<JsonLevel> levelStack;
