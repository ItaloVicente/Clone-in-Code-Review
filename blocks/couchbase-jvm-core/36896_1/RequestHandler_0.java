                public Observable<AddServiceRequest> call(final Map<ServiceType, Integer> services) {
                    List<AddServiceRequest> requests = new ArrayList<AddServiceRequest>(services.size());
                    for (Map.Entry<ServiceType, Integer> service : services.entrySet()) {
                        requests.add( new AddServiceRequest(service.getKey(), config.name(), config.password(),
                            service.getValue(), nodeInfo.hostname()));
                    }
                    return Observable.from(requests);
                }
            }).flatMap(new Func1<AddServiceRequest, Observable<Service>>() {
                @Override
                public Observable<Service> call(AddServiceRequest request) {
