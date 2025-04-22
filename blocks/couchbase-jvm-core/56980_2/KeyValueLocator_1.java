    private static boolean keyIsValid(final BinaryRequest request) {
        if (request.keyBytes() == null || request.keyBytes().length < MIN_KEY_BYTES) {
            request.observable().onError(new IllegalArgumentException("The Document ID must not be null or empty."));
            return false;
        }

        if (request.keyBytes().length > MAX_KEY_BYTES) {
            request.observable().onError(new IllegalArgumentException(
                "The Document ID must not be longer than 250 bytes."));
            return false;
        }

        return true;
    }

