        if (request instanceof SignalFlush) {
            serviceRegistry.services().subscribe(new Action1<Service>() {
                @Override
                public void call(Service service) {
                    service.send(request);
                }
            });
        } else {
            serviceRegistry.locate(request).send(request);
        }
