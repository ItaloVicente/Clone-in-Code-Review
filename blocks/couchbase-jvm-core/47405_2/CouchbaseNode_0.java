        this.serviceStates = new ServiceStateZipper(LifecycleState.DISCONNECTED);

        serviceStates.states().subscribe(new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState newState) {
                LifecycleState oldState = state();
                if (oldState == newState) {
                    return;
                }

                if (newState == LifecycleState.CONNECTED) {
                    if (!connected) {
                        LOGGER.info("Connected to Node " + hostname.getHostName());

                        if (eventBus !=  null) {
                            eventBus.publish(new NodeConnectedEvent(hostname));
                        }
                    }
                    connected = true;
                    LOGGER.debug("Connected (" + state() + ") to Node " + hostname);
                } else if (newState == LifecycleState.DISCONNECTED) {
                    if (connected) {
                        LOGGER.info("Disconnected from Node " + hostname.getHostName());
                        if (eventBus != null) {
                            eventBus.publish(new NodeDisconnectedEvent(hostname));
                        }
                    }
                    connected = false;
                    LOGGER.debug("Disconnected (" + state() + ") from Node " + hostname);
                }
                transitionState(newState);
            }
        });
