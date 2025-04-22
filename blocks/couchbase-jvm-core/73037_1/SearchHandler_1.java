        if (currentRequest() instanceof KeepAliveRequest) {
            if (msg instanceof LastHttpContent) {
                response = new KeepAliveResponse(ResponseStatusConverter.fromHttp(responseHeader.getStatus().code()), currentRequest());
                responseContent.clear();
                responseContent.discardReadBytes();
                finishedDecoding();
            }
        } else if (msg instanceof LastHttpContent) {
