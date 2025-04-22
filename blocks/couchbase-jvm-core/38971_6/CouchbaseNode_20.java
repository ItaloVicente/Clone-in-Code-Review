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
                    LOGGER.info("Connected to Node " + hostname);
                    LOGGER.debug("Connected ("+state()+") to Node " + hostname);
                } else if (newState == LifecycleState.DISCONNECTED) {
                    LOGGER.info("Disconnected from Node " + hostname);
                    LOGGER.debug("Disconnected ("+state()+") from Node " + hostname);
                }
                transitionState(newState);
            }
        });
