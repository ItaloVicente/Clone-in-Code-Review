        serviceStates.put(service, service.state());
        service.states().subscribe(new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState state) {
                serviceStates.put(service, state);
                LifecycleState oldState = state();
                LifecycleState newState = recalculateState();
                if (oldState == newState) {
                    return;
                }

                if (newState == LifecycleState.CONNECTED) {
                    LOGGER.debug("Connected ("+state()+") to Node " + hostname);
                } else if (newState == LifecycleState.DISCONNECTED) {
                    LOGGER.debug("Disconnected ("+state()+") from Node " + hostname);
                }
                transitionState(newState);
            }
        });
