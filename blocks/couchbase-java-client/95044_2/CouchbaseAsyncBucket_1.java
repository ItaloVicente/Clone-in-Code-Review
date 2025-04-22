        final Observable<SearchQueryResponse> source = deferAndWatch(
            new Func1<Subscriber, Observable<? extends SearchQueryResponse>>() {
                @Override
                public Observable<? extends SearchQueryResponse> call(Subscriber subscriber) {
                    final SearchQueryRequest request =
                        new SearchQueryRequest(indexName, query.export().toString(), bucket, username, password);
                    request.subscriber(subscriber);
                    Utils.addRequestSpan(environment, request, "search");
                    return applyTimeout(core.<SearchQueryResponse>send(request), request, environment, timeout, timeUnit);                }
