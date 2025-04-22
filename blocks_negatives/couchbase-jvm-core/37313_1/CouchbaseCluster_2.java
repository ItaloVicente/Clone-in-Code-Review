            requestHandler.addService((AddServiceRequest) request).subscribe(new Observer<Service>() {
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

                    request.observable().onNext(new AddServiceResponse(ResponseStatus.SUCCESS, ((AddServiceRequest) request).hostname()));
                }
            });
