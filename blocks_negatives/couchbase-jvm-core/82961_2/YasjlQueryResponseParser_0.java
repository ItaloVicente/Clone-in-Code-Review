        queryErrorObservable.onBackpressureBuffer();
        queryRowObservable.onBackpressureBuffer();
        querySignatureObservable.onBackpressureBuffer();
        queryStatusObservable.onBackpressureBuffer();
        queryInfoObservable.onBackpressureBuffer();
        queryProfileInfoObservable.onBackpressureBuffer();

        if (!this.callbacksOnIoPool) {
            queryErrorObservable.observeOn(scheduler);
            queryRowObservable.observeOn(scheduler);
            querySignatureObservable.observeOn(scheduler);
            queryStatusObservable.observeOn(scheduler);
            queryInfoObservable.observeOn(scheduler);
            queryProfileInfoObservable.observeOn(scheduler);
        }
