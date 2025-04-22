                    return Observable.from(requests);
                }
            }).flatMap(new Func1<AddServiceRequest, Observable<Service>>() {
                @Override
                public Observable<Service> call(AddServiceRequest request) {
                    return addService(request);
                }
            }).subscribe();
