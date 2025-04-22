        }).doOnTerminate(new Action0() {
            @Override
            public void call() {
                if (parent != null) {
                    environment.tracer().scopeManager()
                        .activate(parent, true)
                        .close();
                }
            }
