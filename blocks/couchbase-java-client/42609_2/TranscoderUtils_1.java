        return (flags & COMMON_FLAGS_MASK) > 0;
    }

    public static boolean hasCompressionFlags(final int flags) {
        return (flags >> 29) > 0;
    }

    public static boolean hasCommonFlags(final int flags, final int expectedCommonFlag) {
        return hasCommonFlags(flags) && (flags & COMMON_FLAGS_MASK) == expectedCommonFlag;
