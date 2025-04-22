            })
            .filter(new Func1<Long, Boolean>() {
                @Override
                public Boolean call(Long aLong) {
                    boolean allowed = allowedToPoll();
                    if (allowed) {
                        lastPollTimestamp = System.nanoTime();
                    } else {
                        LOGGER.trace("Ignoring tainted polling attempt because poll interval is too small.");
                    }
                    return allowed;
                }
