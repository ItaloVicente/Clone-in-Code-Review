        if (msg instanceof StreamRequestRequest) {
            StreamRequestRequest streamRequest = (StreamRequestRequest) msg;
            request = handleStreamRequestRequest(ctx, streamRequest);
            connection = streamRequest.connection();
        } else if (msg instanceof StreamCloseRequest) {
            request = handleStreamCloseRequest(ctx, (StreamCloseRequest) msg);
