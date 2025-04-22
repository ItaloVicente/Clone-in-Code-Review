            switch(status) {
                case CHUNKED:
                    event.getObservable().onNext(response);
                    break;
                case SUCCESS:
                case EXISTS:
                case NOT_EXISTS:
                case FAILURE:
                    event.getObservable().onNext(response);
