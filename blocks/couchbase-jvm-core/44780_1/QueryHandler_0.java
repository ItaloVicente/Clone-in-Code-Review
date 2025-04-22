        String requestId;
        String clientId = "";

        if (responseContent.readableBytes() >= 55) {
            responseContent.skipBytes(bytesBefore(':'));
            responseContent.skipBytes(bytesBefore('"') + 1);
            ByteBuf slice = responseContent.readSlice(36);
            requestId = slice.toString(CHARSET);
        } else {
            return null;
        }

        if (responseContent.readableBytes() >= 27 && bytesBefore(':') < 27) {
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

