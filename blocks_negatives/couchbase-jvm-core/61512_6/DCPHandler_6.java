        if (msg instanceof OpenConnectionRequest) {
            OpenConnectionRequest openConnection = (OpenConnectionRequest) msg;
            request = handleOpenConnectionRequest(ctx, openConnection);
            connection = new DCPConnection(env(), openConnection.connectionName(), openConnection.bucket());
        } else if (msg instanceof StreamRequestRequest) {
            request = handleStreamRequestRequest(ctx, (StreamRequestRequest) msg);
