        final Observable<ViewQueryResponse> source = deferAndWatch(
            new Func1<Subscriber, Observable<? extends ViewQueryResponse>>() {
                @Override
                public Observable<? extends ViewQueryResponse> call(final Subscriber subscriber) {
                    final ViewQueryRequest request = new ViewQueryRequest(query.getDesign(), query.getView(),
                        query.isDevelopment(), query.toQueryString(), query.getKeys(), bucket, username, password);
                    request.subscriber(subscriber);
                    Utils.addRequestSpan(environment, request, "view");
                    return applyTimeout(core.<ViewQueryResponse>send(request), request, environment, timeout, timeUnit);
                }
