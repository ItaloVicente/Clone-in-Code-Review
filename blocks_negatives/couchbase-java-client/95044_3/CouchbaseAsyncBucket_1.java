        Observable<SearchQueryResponse> source = Observable.defer(new Func0<Observable<SearchQueryResponse>>() {
            @Override
            public Observable<SearchQueryResponse> call() {
                final SearchQueryRequest request =
                    new SearchQueryRequest(indexName, query.export().toString(), bucket, username, password);
                Utils.addRequestSpan(environment, request, "search");
                return applyTimeout(core.<SearchQueryResponse>send(request), request, environment, timeout, timeUnit);
            }
