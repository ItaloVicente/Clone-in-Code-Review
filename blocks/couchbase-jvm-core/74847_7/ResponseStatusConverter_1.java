    public static void updateBinaryErrorMap(final ErrorMap map) {
        if (map == null) {
            return;
        }

        if (BINARY_ERROR_MAP == null || map.compareTo(BINARY_ERROR_MAP) > 0) {
            BINARY_ERROR_MAP = map;
        }
    }

