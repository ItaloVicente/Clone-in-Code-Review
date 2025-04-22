        queryRowObservable.withTraceIdentifier("queryRow." + rid).onBackpressureBuffer();
        queryErrorObservable.withTraceIdentifier("queryError." + rid).onBackpressureBuffer();
        queryInfoObservable.withTraceIdentifier("queryInfo." + rid).onBackpressureBuffer();
        querySignatureObservable.withTraceIdentifier("querySignature." + rid).onBackpressureBuffer();
        queryStatusObservable.onBackpressureBuffer();

        if (!env().callbacksOnIoPool()) {
            queryErrorObservable.observeOn(scheduler);
            queryRowObservable.observeOn(scheduler);
            querySignatureObservable.observeOn(scheduler);
            queryStatusObservable.observeOn(scheduler);
            queryInfoObservable.observeOn(scheduler);
        }
