                }).flatMap(new Func1<AddServiceRequest, Observable<Service>>() {
                    @Override
                    public Observable<Service> call(AddServiceRequest request) {
                        return addService(request);
                    }
                }).last().map(new Func1<Service, Boolean>() {
                        @Override
                        public Boolean call(Service service) {
                            return true;
                        }
                    });
            observables.add(obs);
