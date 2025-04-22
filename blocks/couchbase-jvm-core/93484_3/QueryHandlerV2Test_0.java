

    @Test
    public void shouldDiscardReadBytesOnChunkedResponse() throws Throwable {
        String response = Resources.read("chunked.json", this.getClass());
        ArrayList<String> chunks = new ArrayList<String>();
        int i = 0;
        while(i+100 < response.length()) {
            chunks.add(response.substring(i, i+100));
            i = i+100;
        }
        chunks.add(response.substring(i));
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        Object[] httpChunks = new Object[chunks.size() + 1];
        httpChunks[0] = responseHeader;
        for (i = 1; i <= chunks.size(); i++) {
            String chunk = chunks.get(i - 1);
            if (i == chunks.size()) {
                httpChunks[i] = new DefaultLastHttpContent(Unpooled.copiedBuffer(chunk, CharsetUtil.UTF_8));
            } else {
                httpChunks[i] = new DefaultHttpContent(Unpooled.copiedBuffer(chunk, CharsetUtil.UTF_8));
            }
        }

        Subject<CouchbaseResponse, CouchbaseResponse> obs = AsyncSubject.create();
        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        when(requestMock.observable()).thenReturn(obs);
        queue.add(requestMock);
        final CountDownLatch latch1 = new CountDownLatch(chunks.size() + 5 + 4);

        obs.subscribe(new Action1<CouchbaseResponse>() {
                          @Override
                          public void call(CouchbaseResponse couchbaseResponse) {
                              GenericQueryResponse response = (GenericQueryResponse) couchbaseResponse;
                              response.rows().subscribe(new Action1<ByteBuf>() {
                                  @Override
                                  public void call(ByteBuf byteBuf) {
                                      byteBuf.release();
                                      if (((QueryHandlerV2) handler).getResponseContent() != null) {
                                          assertTrue(((QueryHandlerV2) handler).getResponseContent().readerIndex() == 1);
                                      }
                                      latch1.countDown();
                                  }
                              }, new Action1<Throwable>() {
                                  @Override
                                  public void call(Throwable throwable) {
                                  }
                              });
                              response.errors().subscribe(new Action1<ByteBuf>() {
                                  @Override
                                  public void call(ByteBuf byteBuf) {
                                      byteBuf.release();
                                      if (((QueryHandlerV2) handler).getResponseContent() != null) {
                                          assertTrue(((QueryHandlerV2) handler).getResponseContent().readerIndex() == 1);
                                      }
                                      latch1.countDown();
                                  }
                              }, new Action1<Throwable>() {
                                  @Override
                                  public void call(Throwable throwable) {

                                  }
                              });
                              response.info().subscribe(new Action1<ByteBuf>() {
                                  @Override
                                  public void call(ByteBuf byteBuf) {
                                      byteBuf.release();
                                  }
                              }, new Action1<Throwable>() {
                                  @Override
                                  public void call(Throwable throwable) {

                                  }
                              });
                              response.signature().subscribe(new Action1<ByteBuf>() {
                                  @Override
                                  public void call(ByteBuf byteBuf) {
                                      byteBuf.release();
                                  }
                              }, new Action1<Throwable>() {
                                  @Override
                                  public void call(Throwable throwable) {

                                  }
                              });

                          }
                      }
        );

        Observable.from(httpChunks).zipWith(Observable.interval(1, TimeUnit.SECONDS),
                new Func2<Object, Long, Object>() {
                    @Override
                    public Object call(Object o, Long aLong) {

                        return channel.writeInbound(o);
                    }
                }).subscribe(new Action1<Object>() {

            @Override
            public void call(Object s) {
                latch1.countDown();
            }

        });
        latch1.await();
    }

