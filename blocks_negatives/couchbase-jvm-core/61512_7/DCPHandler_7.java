        if (connection != null) {
            connection.setLastContext(ctx);
        }
        if (msg.getOpcode() == OP_OPEN_CONNECTION && request instanceof OpenConnectionRequest) {
            response = new OpenConnectionResponse(ResponseStatusConverter.fromBinary(msg.getStatus()), connection, request);
            if (env().dcpConnectionBufferSize() > 0) {
                ctx.writeAndFlush(controlRequest(ctx, ControlParameter.CONNECTION_BUFFER_SIZE, env().dcpConnectionBufferSize()));
            }
        } else if (msg.getOpcode() == OP_STREAM_REQUEST && request instanceof StreamRequestRequest) {
