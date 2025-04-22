				if (!query.params().deferred()) {
					final Observable<AsyncAnalyticsQueryRow> rows = response.rows().map(new Func1<ByteBuf, AsyncAnalyticsQueryRow>() {
						@Override
						public AsyncAnalyticsQueryRow call(ByteBuf byteBuf) {
							try {
								TranscoderUtils.ByteBufToArray rawData = TranscoderUtils.byteBufToByteArray(byteBuf);
								byte[] copy = Arrays.copyOfRange(rawData.byteArray, rawData.offset, rawData.offset + rawData.length);
								return new DefaultAsyncAnalyticsQueryRow(copy);
							} catch (Exception e) {
								throw new TranscodingException("Could not decode Analytics Query Row.", e);
							} finally {
								byteBuf.release();
							}
						}
					});
					AsyncAnalyticsQueryResult r = new DefaultAsyncAnalyticsQueryResult(rows, signature, info, errors,
							finalStatus, parseSuccess, requestId, contextId);
					return Observable.just(r);
				} else {
					String statusHandleStr = response.handle();
					AsyncAnalyticsDeferredResultHandle handle = new DefaultAsyncAnalyticsDeferredResultHandle(statusHandleStr, env, core, bucket, username, password, timeout, timeUnit);
					AsyncAnalyticsQueryResult r = new DefaultAsyncAnalyticsQueryResult(handle, signature, info, errors,
							finalStatus, parseSuccess, requestId, contextId);
					return Observable.just(r);
				}
