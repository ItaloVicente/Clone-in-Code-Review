    @JsonCreator
    public static BucketNodeLocator fromConfig(final String text) {
        if (text == null) {
            return null;
        }
        return valueOf(text.toUpperCase());
    }
