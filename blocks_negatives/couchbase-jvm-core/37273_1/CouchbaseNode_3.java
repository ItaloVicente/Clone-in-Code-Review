            serviceRegistry.services().subscribe(new Action1<Service>() {
                @Override
                public void call(Service service) {
                    service.send(request);
                }
            });
