            .flatMap(new Func1<AsyncN1qlQueryResult, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(final AsyncN1qlQueryResult aqr) {
                    return aqr.finalSuccess()
                              .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                  @Override
                                  public Observable<Boolean> call(Boolean success) {
                                      if (success) {
                                          return Observable.just(true);
                                      } else {
                                          return aqr.errors().toList()
