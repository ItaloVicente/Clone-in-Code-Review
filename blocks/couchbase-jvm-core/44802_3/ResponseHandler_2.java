        try {
            CouchbaseMessage message = event.getMessage();
            if (message instanceof SignalConfigReload) {
                configurationProvider.signalOutdated();
            } else if (message instanceof CouchbaseResponse) {
                CouchbaseResponse response = (CouchbaseResponse) message;
                ResponseStatus status = response.status();
                switch (status) {
                    case SUCCESS:
                    case EXISTS:
                    case NOT_EXISTS:
                    case FAILURE:
                        event.getObservable().onNext(response);
                        event.getObservable().onCompleted();
                        break;
                    case RETRY:
                        retry(event);
                        break;
                    default:
                        throw new UnsupportedOperationException("The ResponseStatus " + status + " is not supported.");
                }
            } else if (message instanceof CouchbaseRequest) {
                retry(event);
            } else {
                throw new IllegalStateException("Got message type I do not understand: " + message);
