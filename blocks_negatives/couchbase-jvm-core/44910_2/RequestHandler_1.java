        if (found == null) {
            event.setRequest(null);
            return;
        }
        if (found.length == 0) {
            responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable());
            event.setRequest(null);
        }
        for (int i = 0; i < found.length; i++) {
            try {
                found[i].send(request);
            } catch (Exception ex) {
                request.observable().onError(ex);
            } finally {
                event.setRequest(null);
