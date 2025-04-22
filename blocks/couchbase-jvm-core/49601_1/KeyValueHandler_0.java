           maybeFreeContent(request);
        }

        msg.content().retain();
        CouchbaseResponse response = handleCommonResponseMessages(request, msg, ctx, status);

        if (response == null) {
            response = handleOtherResponseMessages(request, msg, status);
        }

        if (response == null) {
            throw new IllegalStateException("Unhandled request/response pair: " + request.getClass() + "/"
                    + msg.getClass());
