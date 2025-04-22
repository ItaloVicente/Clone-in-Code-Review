            if (found.length == 0) {
                responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable());
            }
            for (int i = 0; i < found.length; i++) {
                try {
                    found[i].send(request);
                } catch (Exception ex) {
                    request.observable().onError(ex);
                }
            }
            if (endOfBatch) {
                for (Node node : nodes) {
                    node.send(SignalFlush.INSTANCE);
                }
