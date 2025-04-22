                    return service.connect().doOnNext(new Action1<LifecycleState>(){
                            public void call(LifecycleState state) {
                                if (state == LifecycleState.CONNECTED) {
                                    eventBus.publish(new ServiceConnectedEvent(hostname(), service.type()));
                                }
                            }
                        });
