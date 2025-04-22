                queryErrorObservable.onBackpressureBuffer().observeOn(scheduler),
                queryRowObservable.onBackpressureBuffer().observeOn(scheduler),
                queryStatusObservable.onBackpressureBuffer().observeOn(scheduler),
                queryInfoObservable.onBackpressureBuffer().observeOn(scheduler),
                currentRequest(),
                status, requestId, clientId
