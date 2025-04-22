        String requestId;
        String clientId = "";

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_REQUESTID) {
            responseContent.skipBytes(bytesBeforeInResponse(':'));
            responseContent.skipBytes(bytesBeforeInResponse('"') + 1);
            int endOfId = bytesBeforeInResponse('"');
            ByteBuf slice = responseContent.readSlice(endOfId);
            requestId = slice.toString(CHARSET);
        } else {
            return null;
        }

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && bytesBeforeInResponse(':') < MINIMUM_WINDOW_FOR_CLIENTID_TOKEN) {
            responseContent.markReaderIndex();
            ByteBuf slice = responseContent.readSlice(bytesBeforeInResponse(':'));
            if (slice.toString(CHARSET).contains("clientContextID")) {
                responseContent.skipBytes(bytesBeforeInResponse('"') + 1); //opening of clientId
                int clientIdSize = bytesBeforeInResponse('"');
                if (clientIdSize < 0) {
                    return null;
                }
                clientId = responseContent.readSlice(clientIdSize).toString(CHARSET);
                responseContent.skipBytes(1);//closing quote
                responseContent.skipBytes(bytesBeforeInResponse('"')); //next token's quote
            } else {
                responseContent.resetReaderIndex();
            }
        }

