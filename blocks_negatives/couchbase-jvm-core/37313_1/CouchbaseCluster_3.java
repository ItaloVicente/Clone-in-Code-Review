            requestHandler.removeService((RemoveServiceRequest) request).subscribe(new Observer<Service>() {
                @Override
                public void onCompleted() {
                    request.observable().onCompleted();
                }

                @Override
                public void onError(Throwable e) {
                    request.observable().onError(e);
                }

                @Override
                public void onNext(Service service) {
                    request.observable().onNext(null);
                }
            });
