        return Observable
            .from(FLUSH_MARKERS)
            .flatMap(new Func1<String, Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call(String id) {
                    return core.send(new GetRequest(id, bucket));
                }
            })
            .reduce(0, new Func2<Integer, GetResponse, Integer>() {
                @Override
                public Integer call(Integer foundDocs, GetResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
                    if (response.status() == ResponseStatus.SUCCESS) {
                        foundDocs++;
                    }
                    return foundDocs;
                }
            })
            .filter(new Func1<Integer, Boolean>() {
                @Override
                public Boolean call(Integer foundDocs) {
                    return foundDocs == 0;
                }
            })
            .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                @Override
                public Observable<?> call(Observable<? extends Void> observable) {
                    return observable.flatMap(new Func1<Void, Observable<?>>() {
