                } else if (newState == LifecycleState.CONNECTING) {
                    if (connected) {
                        signalDisconnected();
                        connected = false;
                        LOGGER.debug("Reconnecting (" + state() + ") from Node " + hostname);
                    }
