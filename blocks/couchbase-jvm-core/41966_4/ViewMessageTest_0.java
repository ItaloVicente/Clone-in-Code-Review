    @Test
    public void foo() {

            Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<Long, Observable<ViewQueryResponse>>() {
                    @Override
                    public Observable<ViewQueryResponse> call(Long aLong) {
                        return cluster().send(new ViewQueryRequest("beer", "brewery_beers", false, "limit=5", bucket(), password()));
                    }
                })
                .take(Integer.MAX_VALUE)
                .toBlocking()
                .last();
    }

