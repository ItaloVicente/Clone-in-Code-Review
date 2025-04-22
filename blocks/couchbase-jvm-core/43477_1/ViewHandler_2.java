        return new ViewQueryResponse(
            viewRowObservable.onBackpressureBuffer().observeOn(scheduler),
            viewInfoObservable.onBackpressureBuffer().observeOn(scheduler),
            code,
            phrase,
            status,
            currentRequest()
        );
