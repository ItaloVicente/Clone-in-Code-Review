    /**
     * Checks if the flags identify a Long document.
     *
     * Since with common flags a number is also stored with the JSON flag, this is
     * checked. If not, the fallback is the legacy long flag 3 << 8.
     *
     * @param flags the flags to check.
     * @return true if long, false otherwise.
     */
    public static boolean hasLongFlags(final int flags) {
        return hasCommonFlags(flags) ? flags == JSON_COMMON_FLAGS : flags == (3 << 8);
    }

