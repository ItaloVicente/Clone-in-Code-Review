    @Test
    public void shouldThrowErrorWhenQueryingMissingDesignDocument() {
        ViewQuery queryTemplate = ViewQuery.from("test", "dummy");
        queryTemplate.stale(Stale.FALSE);
        Observable<ViewRow> viewRowObservable = bucket().query(queryTemplate).flatMap(new Func1<ViewResult, Observable<ViewRow>>() {
            public Observable<ViewRow> call(ViewResult res) {
                if (res.success()) {
                    return res.rows();
                } else {
                    throw new DesignDocumentException(res.error().getString("error"));
                }
            }
        });

        viewRowObservable.subscribe(new Observer<ViewRow>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                assertEquals("not_found", throwable.getMessage());
            }

            @Override
            public void onNext(ViewRow viewRow) {
                throw new AssertionError("onNext() called on empty results");
            }
        });
    }
