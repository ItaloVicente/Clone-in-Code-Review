        } else if (request instanceof PingRequest) {
            if (msg instanceof LastHttpContent) {
                response = new PingResponse(ResponseStatusConverter.fromHttp(responseHeader.getStatus().code()), request);
                responseContent.clear();
                responseContent.discardReadBytes();
                finishedDecoding();
            }
