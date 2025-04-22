        if (!env().callbacksOnIoPool()) {
            queryErrorObservable.observeOn(scheduler);
            queryRowObservable.observeOn(scheduler);
            querySignatureObservable.observeOn(scheduler);
            queryStatusObservable.observeOn(scheduler);
            queryInfoObservable.observeOn(scheduler);
        }

