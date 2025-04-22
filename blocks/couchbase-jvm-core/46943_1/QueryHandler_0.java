            if (msg instanceof LastHttpContent) {
                response = new KeepAliveResponse(statusFromCode(responseHeader.getStatus().code()), currentRequest());
                responseContent.clear();
                responseContent.discardReadBytes();
                finishedDecoding();
            }
