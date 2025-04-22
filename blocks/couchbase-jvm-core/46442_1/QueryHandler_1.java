        if (responseContent.readableBytes() < MINIMUM_WINDOW_FOR_REQUESTID + MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && !lastChunk) {
            return null; //wait for more data
        }

        int startIndex = responseContent.readerIndex();

