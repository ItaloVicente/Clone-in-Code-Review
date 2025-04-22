		getSystem().getExecutor().execute(() -> {
                    MonotonicClock clk = getSystem().getClock();
                    try (Repository git = getLeader().openRepository();
                            ProposedTimestamp ts = clk.propose()) {
                        try {
                            update(git
                            req.done(git);
                        } catch (Throwable err) {
                            req.setException(git
                        }
                    } catch (IOException err) {
                        req.setException(null
                    }
                });
