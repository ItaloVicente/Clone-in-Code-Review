                    return service.disconnect().doOnNext(new Action1<LifecycleState>() {
                        public void call(LifecycleState state){
                            if (state == LifecycleState.DISCONNECTED) {
                                signalServiceDisconnected(service.type());
                            }
                        }});
