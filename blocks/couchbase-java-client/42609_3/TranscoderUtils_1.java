    public static boolean hasCompressionFlags(final int flags) {
        return (flags >> 29) > 0;
    }

    public static boolean hasCommonFormat(final int flags,
        final int expectedCommonFlag) {
        return hasCommonFlags(flags) && (flags & COMMON_FORMAT_MASK) == expectedCommonFlag;
    }

