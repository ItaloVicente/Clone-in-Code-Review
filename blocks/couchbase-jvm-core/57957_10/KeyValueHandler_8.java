        } else if (request instanceof BinarySubdocRequest) {
            content = ((BinarySubdocRequest) request).content();
        } else if (request instanceof BinarySubdocMultiLookupRequest) {
            content = ((BinarySubdocMultiLookupRequest) request).content();
        } else if (request instanceof BinarySubdocMultiMutationRequest) {
            content = ((BinarySubdocMultiMutationRequest) request).content();
