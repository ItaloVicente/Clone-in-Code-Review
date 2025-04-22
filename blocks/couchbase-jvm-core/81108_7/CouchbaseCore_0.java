            if (coreSendHook == null) {
                boolean published = requestRingBuffer.tryPublishEvent(REQUEST_TRANSLATOR, request);
                if (!published) {
                    request.observable().onError(BACKPRESSURE_EXCEPTION);
                }
                return (Observable<R>) request.observable();
            } else {
                Subject<CouchbaseResponse, CouchbaseResponse> response = request.observable();
                Tuple2<CouchbaseRequest, Observable<CouchbaseResponse>> hook = coreSendHook
                        .beforeSend(request, response);
                boolean published = requestRingBuffer.tryPublishEvent(REQUEST_TRANSLATOR, hook.value1());
                if (!published) {
                    response.onError(BACKPRESSURE_EXCEPTION);
                }
                return (Observable<R>) hook.value2();
