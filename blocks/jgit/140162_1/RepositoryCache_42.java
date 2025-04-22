			cleanupTask = scheduler.scheduleWithFixedDelay(() -> {
                            try {
                                cache.clearAllExpired();
                            } catch (Throwable e) {
                                LOG.error(e.getMessage()
                            }
                        }
