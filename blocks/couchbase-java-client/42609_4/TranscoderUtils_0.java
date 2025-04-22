    public static final int SERIALIZED_LEGACY_FLAGS = 1;
    public static final int BINARY_LEGACY_FLAGS = (8 << 8);
    public static final int STRING_LEGACY_FLAGS = 0;
    public static final int JSON_LEGACY_FLAGS = STRING_LEGACY_FLAGS;
    public static final int BOOLEAN_LEGACY_FLAGS = STRING_LEGACY_FLAGS;
    public static final int LONG_LEGACY_FLAGS = STRING_LEGACY_FLAGS;
    public static final int DOUBLE_LEGACY_FLAGS = STRING_LEGACY_FLAGS;


    public static final int SERIALIZED_COMPAT_FLAGS = PRIVATE_COMMON_FLAGS  | SERIALIZED_LEGACY_FLAGS;
    public static final int JSON_COMPAT_FLAGS       = JSON_COMMON_FLAGS     | JSON_LEGACY_FLAGS;
    public static final int BINARY_COMPAT_FLAGS     = BINARY_COMMON_FLAGS   | BINARY_LEGACY_FLAGS;
    public static final int BOOLEAN_COMPAT_FLAGS    = JSON_COMMON_FLAGS     | BOOLEAN_LEGACY_FLAGS;
    public static final int LONG_COMPAT_FLAGS       = JSON_COMMON_FLAGS     | LONG_LEGACY_FLAGS;
    public static final int DOUBLE_COMPAT_FLAGS     = JSON_COMMON_FLAGS     | DOUBLE_LEGACY_FLAGS;
    public static final int STRING_COMPAT_FLAGS     = STRING_COMMON_FLAGS   | STRING_LEGACY_FLAGS;
