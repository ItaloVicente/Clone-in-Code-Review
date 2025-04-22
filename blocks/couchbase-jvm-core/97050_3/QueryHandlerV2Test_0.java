        Observable.from(httpChunks).zipWith(Observable.interval(1, TimeUnit.SECONDS).takeWhile(new Func1<Long, Boolean>() {
                @Override
                public Boolean call(Long aLong) {
                    return latch1.getCount() > 0;
                }
            }),
