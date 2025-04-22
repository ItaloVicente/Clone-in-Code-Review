    @Test
    public void shouldHavePipeliningEnabled() {
        Subject<CouchbaseResponse,CouchbaseResponse> obs1 = AsyncSubject.create();
        GetRequest requestMock1 = mock(GetRequest.class);
        when(requestMock1.keyBytes()).thenReturn("hello".getBytes());
        when(requestMock1.bucket()).thenReturn("foo");
        when(requestMock1.observable()).thenReturn(obs1);

        Subject<CouchbaseResponse,CouchbaseResponse> obs2 = AsyncSubject.create();
        GetRequest requestMock2 = mock(GetRequest.class);
        when(requestMock2.keyBytes()).thenReturn("hello".getBytes());

        when(requestMock2.bucket()).thenReturn("foo");
        when(requestMock2.observable()).thenReturn(obs2);


        TestSubscriber<CouchbaseResponse> t1 = TestSubscriber.create();
        TestSubscriber<CouchbaseResponse> t2 = TestSubscriber.create();

        obs1.subscribe(t1);
        obs2.subscribe(t2);

        channel.writeOutbound(requestMock1, requestMock2);

        t1.assertNotCompleted();
        t2.assertNotCompleted();
    }

