                          Observable<GetResponse> result = deferAndWatch(new Func1<Subscriber, Observable<GetResponse>>() {
                              @Override
                              public Observable<GetResponse> call(Subscriber subscriber) {
                                  request.subscriber(subscriber);
                                  return core.send(request);
                              }
                          }).filter(new Get.GetFilter(environment));
