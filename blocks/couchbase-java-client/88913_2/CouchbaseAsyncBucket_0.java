    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> get(D document, long timeout, TimeUnit timeUnit) {
        return (Observable<D>) get(document.id(), document.getClass(), timeout, timeUnit);
    }

    @Override
    public <D extends Document<?>> Observable<D> get(final String id, final Class<D> target, final long timeout,
        final TimeUnit timeUnit) {
        return deferAndWatch(new Func1<Subscriber, Observable<GetResponse>>() {
            @Override
            public Observable<GetResponse> call(Subscriber s) {
                final GetRequest request = new GetRequest(id, bucket);
                request.subscriber(s);
                Observable<GetResponse> response = core.send(request);
                if (timeout > 0) {
                    response = response
                      .timeout(timeout, timeUnit, environment.scheduler())
                       .onErrorResumeNext(new Func1<Throwable, Observable<GetResponse>>() {
                           @Override
                           public Observable<GetResponse> call(Throwable throwable) {
                               if (throwable instanceof TimeoutException) {
                                   String message = "type: kv, id: "
                                     + request.opaque()
                                     + ", local: " + request.dispatchLocal()
                                     + ", remote: " + request.dispatchRemote()
                                     + ", observed_time_us: " + timeUnit.toMicros(timeout) + "µs";
                                   return Observable.error(new TimeoutException(message));
                               }
                               return Observable.error(throwable);
                           }
                       });
                }
                return response;
            }
        })
          .filter(new Func1<GetResponse, Boolean>() {
              @Override
              public Boolean call(GetResponse response) {
                  if (response.status().isSuccess()) {
                      return true;
                  }
                  ByteBuf content = response.content();
                  if (content != null && content.refCnt() > 0) {
                      content.release();
                  }

                  switch(response.status()) {
                      case NOT_EXISTS:
                          return false;
                      case TEMPORARY_FAILURE:
                      case SERVER_BUSY:
                          throw addDetails(new TemporaryFailureException(), response);
                      case OUT_OF_MEMORY:
                          throw addDetails(new CouchbaseOutOfMemoryException(), response);
                      default:
                          throw addDetails(new CouchbaseException(response.status().toString()), response);
                  }
              }
          })
          .map(new Func1<GetResponse, D>() {
              @Override
              public D call(final GetResponse response) {
                  Transcoder<?, Object> transcoder = (Transcoder<?, Object>) transcoders.get(target);
                  return (D) transcoder.decode(id, response.content(), response.cas(), 0, response.flags(),
                    response.status());
              }
          });    }

