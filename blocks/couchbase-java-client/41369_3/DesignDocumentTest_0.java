    @Test
    public void shouldUserOnErrorCallbackWhenQueryingMissingDesignDocumentAndOnErrorSetToStop() {
        ViewQuery queryTemplate = ViewQuery.from("test", "dummy");
        queryTemplate.stale(Stale.FALSE).onError(OnError.STOP);
        Observable<ViewRow> viewRowObservable = bucket().query(queryTemplate).flatMap(new Func1<ViewResult, Observable<ViewRow>>() {
            public Observable<ViewRow> call(ViewResult res) {
                return res.rows();
            }
        });

        viewRowObservable.subscribe(new Observer<ViewRow>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                assertTrue(throwable instanceof ViewQueryException);
                assertEquals("not_found", ((ViewQueryException) throwable).getError());
            }

            @Override
            public void onNext(ViewRow viewRow) {
                throw new AssertionError("onNext() called on empty results");
            }
        });
    }

    @Test
    public void shouldReturnErrorInResultObjectWhenQueryingMissingDesignDocumentAndOnErrorSetToContinue() {
        ViewQuery queryTemplate = ViewQuery.from("test", "dummy");
        queryTemplate.stale(Stale.FALSE).onError(OnError.CONTINUE);
        Observable<ViewRow> viewRowObservable = bucket().query(queryTemplate).flatMap(new Func1<ViewResult, Observable<ViewRow>>() {
            public Observable<ViewRow> call(ViewResult res) {
                assertFalse(res.success());
                assertEquals("not_found", res.error().getString("error"));
                return res.rows();
            }
        });

        viewRowObservable.subscribe(new Observer<ViewRow>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                throw new AssertionError("onError() called when OnError.CONTINUE specified");
            }

            @Override
            public void onNext(ViewRow viewRow) {
                throw new AssertionError("onNext() called on empty results");
            }
        });
    }
