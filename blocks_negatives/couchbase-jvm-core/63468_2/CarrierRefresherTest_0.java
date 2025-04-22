        ByteBuf content = Unpooled.copiedBuffer("{\"config\": true}", CharsetUtil.UTF_8);
        when(cluster.send(any(GetBucketConfigRequest.class))).thenReturn(Observable.just(
            (CouchbaseResponse) new GetBucketConfigResponse(
                ResponseStatus.SUCCESS, KeyValueStatus.SUCCESS.code(),
                "bucket",
                content,
                InetAddress.getByName("localhost")
            )
        ));
