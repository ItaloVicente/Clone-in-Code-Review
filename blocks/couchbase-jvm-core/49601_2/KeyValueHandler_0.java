           maybeFreeContent(request);
        }

        msg.content().retain();
        CouchbaseResponse response = handleCommonResponseMessages(request, msg, ctx, status);

        if (response == null) {
            response = handleOtherResponseMessages(request, msg, status);
