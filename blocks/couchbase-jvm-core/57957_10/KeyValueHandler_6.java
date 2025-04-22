        if (response == null) {
            response = handleSubdocumentResponseMessages(request, msg, ctx, status, seqOnMutation);
        }

        if (response == null) {
            response = handleSubdocumentMultiLookupResponseMessages(request, msg, ctx, status);
        }

        if (response == null) {
            response = handleSubdocumentMultiMutationResponseMessages(request, msg, ctx, status, seqOnMutation);
        }

