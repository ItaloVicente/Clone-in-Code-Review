    private boolean isEligibleForCompression(final BinaryRequest msg) {
        return msg instanceof BinaryStoreRequest
            || msg instanceof AppendRequest
            || msg instanceof PrependRequest;
    }

