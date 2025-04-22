                }).flatMap(new Func1<Map<ServiceType, Integer>, Observable<AddServiceRequest>>() {
                    @Override
                    public Observable<AddServiceRequest> call(final Map<ServiceType, Integer> services) {
                        List<AddServiceRequest> requests = new ArrayList<AddServiceRequest>(services.size());
                        for (Map.Entry<ServiceType, Integer> service : services.entrySet()) {
                            requests.add(new AddServiceRequest(service.getKey(), config.name(), config.password(),
                                    service.getValue(), nodeInfo.hostname()));
                        }
                        return Observable.from(requests);
