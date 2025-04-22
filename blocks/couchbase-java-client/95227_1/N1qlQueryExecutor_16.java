            source = deferAndWatch(new Func1<Subscriber, Observable<? extends GenericQueryResponse>>() {
				@Override
				public Observable<? extends GenericQueryResponse> call(Subscriber subscriber) {
					GenericQueryRequest request = createN1qlRequest(query, bucket, username, password, null);
					request.subscriber(subscriber);
					return core.send(request);
