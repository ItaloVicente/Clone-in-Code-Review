        String requestId;
        String clientId = "";

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_REQUESTID) {
            responseContent.skipBytes(bytesBefore(':'));
            responseContent.skipBytes(bytesBefore('"') + 1);
            int endOfId = bytesBefore('"');
            ByteBuf slice = responseContent.readSlice(endOfId);
            requestId = slice.toString(CHARSET);
        } else {
            return null;
        }

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && bytesBefore(':') < MINIMUM_WINDOW_FOR_CLIENTID_TOKEN) {
            responseContent.markReaderIndex();
            ByteBuf slice = responseContent.readSlice(bytesBefore(':'));
            if (slice.toString(CHARSET).contains("clientContextID")) {
                responseContent.skipBytes(bytesBefore('"') + 1); //opening of clientId
                int clientIdSize = bytesBefore('"');
                if (clientIdSize < 0) {
                    return null;
                }
                clientId = responseContent.readSlice(clientIdSize).toString(CHARSET);
                responseContent.skipBytes(1);//closing quote
                responseContent.skipBytes(bytesBefore('"')); //next token's quote
            } else {
                responseContent.resetReaderIndex();
            }
        }

