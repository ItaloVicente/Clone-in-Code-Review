                            if (future.channel() == null) {
                                Observable.timer(delay, delayUnit).subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long ignored) {
                                        LOGGER.debug("Firing reconnect timer from RX because the connect future"
                                            + "timeout hit and no channel is available to scheudle on.");
                                        if (!disconnected) {
                                            doConnect(observable, bootstrapping);
                                        } else {
                                            LOGGER.debug("{}Explicitly breaking retry loop because already disconnected.",
