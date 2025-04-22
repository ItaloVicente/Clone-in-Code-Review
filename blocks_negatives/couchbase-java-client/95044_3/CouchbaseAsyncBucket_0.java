        Observable<ViewQueryResponse> source = Observable.defer(new Func0<Observable<ViewQueryResponse>>() {
            @Override
            public Observable<ViewQueryResponse> call() {
                final ViewQueryRequest request = new ViewQueryRequest(query.getDesign(), query.getView(),
                    query.isDevelopment(), query.toQueryString(), query.getKeys(), bucket, username, password);
                Utils.addRequestSpan(environment, request, "view");
                return applyTimeout(core.<ViewQueryResponse>send(request), request, environment, timeout, timeUnit);
            }
