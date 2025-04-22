        Endpoint endpoint = strategy.select(request, endpoints);
        if (endpoint == null) {
            request.observable().onError(new IllegalStateException("Endpoint not found for request: " + request));
        } else {
            endpoint.send(request);
        }
