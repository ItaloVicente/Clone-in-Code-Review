    public static void ignoreIfMocked() {
        Assume.assumeFalse(useMock);
    }

    public static boolean useMock() {
        return useMock;
    }

