    public Observable<Service> services() {
        return Observable.create(new Observable.OnSubscribe<Service>() {
            @Override
            public void call(Subscriber<? super Service> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        for (Service service : globalServices.values()) {
                            subscriber.onNext(service);
                        }
                        for (Map<ServiceType, Service> bucket : localServices.values()) {
                            for (Service service : bucket.values()) {
                                subscriber.onNext(service);
                            }
                        }
                        subscriber.onCompleted();
                    } catch (Exception ex) {
                        subscriber.onError(ex);
                    }
                }
