                        })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        instanceCounter--;
                    }
                });
