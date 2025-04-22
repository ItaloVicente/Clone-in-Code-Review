                .subscribe(new Action1<LifecycleState>() {
                    @Override
                    public void call(LifecycleState state) {
                        if (state == state()) {
                            return;
                        }
                        if (state == LifecycleState.CONNECTED) {
                            LOGGER.debug(logIdent(hostname, AbstractService.this) + "Connected Service.");
                        } else if (state == LifecycleState.DISCONNECTED) {
                            LOGGER.debug(logIdent(hostname, AbstractService.this) + "Disconnected Service.");
                        }
                        transitionState(state);
