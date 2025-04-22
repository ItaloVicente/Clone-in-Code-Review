                    listener.dragStart(event);
                }
            });
                transfers.add(listener.getTransfer());
                activeListeners.add(listener);
            }
            doit |= event.doit;
        }
