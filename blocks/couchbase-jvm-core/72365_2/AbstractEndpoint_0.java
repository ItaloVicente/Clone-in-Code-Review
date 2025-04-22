                    if (!pipeline) {
                        free = false;
                        request.observable().subscribe(new Subscriber<CouchbaseResponse>() {
                            @Override
                            public void onCompleted() {
                                free = true;
                            }

                            @Override
                            public void onError(Throwable e) {
                                free = true;
                            }

                            @Override
                            public void onNext(CouchbaseResponse couchbaseResponse) {
                            }
                        });
                    }
