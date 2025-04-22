        CouchbaseResponse response = event.getResponse();
        ResponseStatus status = response.status();
        if (status == ResponseStatus.CHUNKED || status == ResponseStatus.SUCCESS) {
            event.getObservable().onNext(response);
            if (status == ResponseStatus.SUCCESS) {
                event.getObservable().onCompleted();
