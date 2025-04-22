                    if (state == LifecycleState.CONNECTED) {
                        LOGGER.debug(logIdent(hostname, AbstractService.this) + "Connected Service.");
                    } else if (state == LifecycleState.DISCONNECTED) {
                        LOGGER.debug(logIdent(hostname, AbstractService.this) + "Disconnected Service.");
                    }
                    transitionState(state);
                }
            });
