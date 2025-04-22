		newRepo.getListenerList().addRefsChangedListener((RefsChangedEvent event) -> {
                    try {
                        refDb.getRefsByPrefix("ref");
                        changeCount.incrementAndGet();
                    } catch (StackOverflowError soe) {
                        error.set(soe);
                    } catch (IOException ioe) {
                        exception.set(ioe);
                    }
                });
