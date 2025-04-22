        if (!status.equals(ResponseStatus.RETRY)) {
            if (request instanceof BinaryStoreRequest) {
                ((BinaryStoreRequest) request).content().release();
            } else if (request instanceof AppendRequest) {
                ((AppendRequest) request).content().release();
            } else if (request instanceof PrependRequest) {
                ((PrependRequest) request).content().release();
            }
        }

