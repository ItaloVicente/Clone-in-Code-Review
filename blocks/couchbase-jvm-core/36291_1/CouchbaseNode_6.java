            Service service = serviceRegistry.locate(request);
            if (service == null) {
                request.observable().onError(new IllegalStateException("Service not found for request: " + request));
            } else {
                service.send(request);
            }
