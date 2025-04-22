                }).map(new Func1<Boolean, Boolean>() {
                @Override
                public Boolean call(Boolean success) {
                    requestDisruptor.shutdown();
                    responseDisruptor.shutdown();
                    disruptorExecutor.shutdownNow();
                    return success;
                }
            })
