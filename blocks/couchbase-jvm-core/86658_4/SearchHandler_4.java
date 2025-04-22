        } else if (currentRequest() instanceof PingRequest) {
            if (msg instanceof LastHttpContent) {
                response = new PingResponse(ResponseStatusConverter.fromHttp(responseHeader.getStatus().code()), currentRequest());
                responseContent.clear();
                responseContent.discardReadBytes();
                finishedDecoding();
            }
