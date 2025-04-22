        return new GenericQueryResponse(
            queryRowObservable.onBackpressureBuffer().observeOn(scheduler),
            queryInfoObservable.onBackpressureBuffer().observeOn(scheduler),
            status,
            currentRequest()
        );
