        Subject<CouchbaseResponse, CouchbaseResponse> response = AsyncSubject.create();
        response.onNext(new UpsertResponse(
            ResponseStatus.SUCCESS, KeyValueStatus.SUCCESS.code(), 1234, "bucket", Unpooled.EMPTY_BUFFER, null,
            mock(CouchbaseRequest.class)
        ));
        response.onCompleted();

        when(core.send(any(UpsertRequest.class))).thenReturn(response);
