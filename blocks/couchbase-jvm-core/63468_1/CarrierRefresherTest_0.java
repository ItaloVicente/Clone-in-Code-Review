        final AtomicReference<ByteBuf> bufRef = new AtomicReference<ByteBuf>(null);
        when(cluster.send(any(GetBucketConfigRequest.class)))
                .thenAnswer(new Answer<Observable<GetBucketConfigResponse>>() {
                    @Override
                    public Observable<GetBucketConfigResponse> answer(InvocationOnMock invocation) throws Throwable {
                        ByteBuf content = Unpooled.copiedBuffer("{\"config\": true}", CharsetUtil.UTF_8);
                        ByteBuf oldContent = bufRef.getAndSet(content);
                        if (oldContent != null) {
                            assertEquals(0, oldContent.refCnt());
                        }
                        GetBucketConfigResponse response = new GetBucketConfigResponse(
                                ResponseStatus.SUCCESS, KeyValueStatus.SUCCESS.code(),
                                "bucket",
                                content,
                                InetAddress.getByName("localhost"));
                        return Observable.just(response);
                    }
                });
