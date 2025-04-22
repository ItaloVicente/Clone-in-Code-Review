
        final Observable<SearchQueryResponse> source = deferAndWatch(
            new Func1<Subscriber, Observable<? extends SearchQueryResponse>>() {
                @Override
                public Observable<? extends SearchQueryResponse> call(Subscriber subscriber) {
                final SearchQueryRequest request =
                    new SearchQueryRequest(indexName, query.export().toString(), bucket, username, password);
                Utils.addRequestSpan(environment, request, "search");
                request.subscriber(subscriber);
                return applyTimeout(core.<SearchQueryResponse>send(request), request, environment, timeout, timeUnit);
            }
        });

        return source.map(new Func1<SearchQueryResponse, AsyncSearchQueryResult>() {
            @Override
            public AsyncSearchQueryResult call(SearchQueryResponse response) {
                if (response.status().isSuccess()) {
                    JsonObject json = JsonObject.fromJson(response.payload());
                    return DefaultAsyncSearchQueryResult.fromJson(json);
                } else if (response.payload().contains("index not found")) {
                    return DefaultAsyncSearchQueryResult.fromIndexNotFound(indexName);
                } else if (response.status() == ResponseStatus.INVALID_ARGUMENTS) {
                    return DefaultAsyncSearchQueryResult.fromHttp400(response.payload());
                } else if (response.status() == ResponseStatus.FAILURE) {
                    return DefaultAsyncSearchQueryResult.fromHttp412();
                } else {
                    throw new CouchbaseException("Could not query search index, " + response.status() + ": " + response.payload());
                }
            }
        });
