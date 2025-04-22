		return deferAndWatch(new Func1<Subscriber, Observable<GenericAnalyticsResponse>>() {
			@Override
			public Observable<GenericAnalyticsResponse> call(final Subscriber subscriber) {
				GenericAnalyticsRequest request = GenericAnalyticsRequest
                    .jsonQuery(query.query().toString(), bucket, username, password);
				request.subscriber(subscriber);
				return core.<GenericAnalyticsResponse>send(request);
