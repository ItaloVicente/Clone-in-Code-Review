        return deferAndWatch(new Func1<Subscriber, Observable<GenericQueryResponse>>() {
			@Override
			public Observable<GenericQueryResponse> call(Subscriber subscriber) {
				GenericQueryRequest request = createN1qlRequest(query, bucket, username, password, null);
				request.subscriber(subscriber);
				return core.send(request);
