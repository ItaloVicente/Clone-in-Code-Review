	public static final Func1<? super AsyncAnalyticsQueryResult, ? extends Observable<? extends AnalyticsQueryResult>> ASYNC_RESULT_TO_SYNC_DEFERRED = new Func1<AsyncAnalyticsQueryResult, Observable<AnalyticsQueryResult>>() {
		@Override
		public Observable<AnalyticsQueryResult> call(final AsyncAnalyticsQueryResult aqr) {
			final boolean parseSuccess = aqr.parseSuccess();
			final String requestId = aqr.requestId();
			final String clientContextId = aqr.clientContextId();

			return Observable.zip(aqr.signature().singleOrDefault(JsonObject.empty()),
					aqr.info().singleOrDefault(AnalyticsMetrics.EMPTY_METRICS),
					aqr.errors().toList(),
					aqr.status(),
					aqr.finalSuccess().singleOrDefault(Boolean.FALSE),
					new Func5<Object, AnalyticsMetrics, List<JsonObject>, String, Boolean, AnalyticsQueryResult>() {
						@Override
						public AnalyticsQueryResult call(Object signature, AnalyticsMetrics info, List<JsonObject> errors, String finalStatus, Boolean finalSuccess) {
							return new DefaultAnalyticsQueryResult(aqr.handle(), signature, info, errors, finalStatus, finalSuccess,
									parseSuccess, requestId, clientContextId);
						}
					});
		}
	};

    protected static final Func1<AsyncAnalyticsQueryResult, Observable<AsyncAnalyticsQueryResult>> RESULT_PEEK_FOR_RETRY =
