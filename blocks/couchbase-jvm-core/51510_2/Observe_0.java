                .map(new Func1<ObserveResponse, ObserveItem>() {
                    @Override
                    public ObserveItem call(ObserveResponse observeResponse) {
                        return new ObserveItem(id, observeResponse, cas, remove, persistIdentifier, replicaIdentifier);
                    }
                })
                .scan(new ObserveItem(),
                        new Func2<ObserveItem, ObserveItem, ObserveItem>() {
                            @Override
                            public ObserveItem call(ObserveItem currentStatus, ObserveItem newStatus) {
                                return currentStatus.add(newStatus);
                            }
                        })
