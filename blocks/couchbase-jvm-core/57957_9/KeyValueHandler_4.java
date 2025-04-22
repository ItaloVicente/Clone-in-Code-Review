        } else if (msg instanceof BinarySubdocRequest) {
            request = handleSubdocumentRequest(ctx, (BinarySubdocRequest) msg);
        } else if (msg instanceof BinarySubdocMultiLookupRequest) {
            request = handleSubdocumentMultiLookupRequest(ctx, (BinarySubdocMultiLookupRequest) msg);
        } else if (msg instanceof BinarySubdocMultiMutationRequest) {
            request = handleSubdocumentMultiMutationRequest(ctx, (BinarySubdocMultiMutationRequest) msg);
