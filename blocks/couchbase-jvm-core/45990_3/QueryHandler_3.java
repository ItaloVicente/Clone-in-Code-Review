        if (currentRequest() instanceof KeepAliveRequest) {
            response = new KeepAliveResponse(statusFromCode(responseHeader.getStatus().code()), currentRequest());
            responseContent.clear();
            responseContent.discardReadBytes();
            finishedDecoding();
        } else if (msg instanceof HttpContent) {
