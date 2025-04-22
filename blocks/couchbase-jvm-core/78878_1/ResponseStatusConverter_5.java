        return ResponseStatus.FAILURE;
    }

    public static ErrorMap.ErrorCode readErrorCodeFromErrorMap(final long code) {
        if (BINARY_ERROR_MAP == null) {
            LOGGER.trace("Binary error map unavailable");
            return null;
        }
        ErrorMap.ErrorCode result = BINARY_ERROR_MAP.errors().get(code);
        return result;
