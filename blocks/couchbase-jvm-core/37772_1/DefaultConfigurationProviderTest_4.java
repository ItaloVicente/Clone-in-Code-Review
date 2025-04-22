            .thenReturn(Observable.from(Tuple.create(LoaderType.Carrier, bucketConfig)));


        final Refresher refresher = mock(Refresher.class);
        when(refresher.configs()).thenReturn(Observable.<BucketConfig>empty());
        when(refresher.registerBucket(anyString(), anyString())).thenReturn(Observable.just(true));
