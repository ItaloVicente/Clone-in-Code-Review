                    return result.error().flatMap(new Func1<JsonObject, Observable<? extends AsyncViewRow>>() {
                        @Override
                        public Observable<? extends AsyncViewRow> call(JsonObject e) {
                            return Observable.error(new CouchbaseException(e.toString()));
                        }
                    }).switchIfEmpty(result.rows());
