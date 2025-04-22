                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DesignDocument>>() {
                    @Override
                    public Observable<? extends DesignDocument> call(Throwable throwable) {
                        if (throwable instanceof DesignDocumentDoesNotExistException) {
                            return Observable.empty();
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                }).isEmpty()
