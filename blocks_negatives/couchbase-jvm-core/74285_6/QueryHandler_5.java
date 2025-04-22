        if (queryRowObservable != null) {
            queryRowObservable.onCompleted();
        }
        if (queryInfoObservable != null) {
            queryInfoObservable.onCompleted();
        }
        if (queryErrorObservable != null) {
            queryErrorObservable.onCompleted();
        }
        if (queryStatusObservable != null) {
            queryStatusObservable.onCompleted();
        }
        if (querySignatureObservable != null) {
            querySignatureObservable.onCompleted();
        }
        cleanupQueryStates();
