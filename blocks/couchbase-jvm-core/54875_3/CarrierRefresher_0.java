
        Observable
            .interval(10, TimeUnit.SECONDS, environment.scheduler())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    provider().signalOutdated();
                }
            });
