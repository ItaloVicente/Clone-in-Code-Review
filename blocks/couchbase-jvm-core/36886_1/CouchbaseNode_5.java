        Service addedService = serviceRegistry.serviceBy(request.type(), request.bucket());
        if (addedService != null) {
            return Observable.from(addedService);
        }

        final Service service = ServiceFactory.create(
            request.hostname(),
            request.bucket(),
            request.password(),
            environment,
            request.type(),
            responseBuffer
        );
