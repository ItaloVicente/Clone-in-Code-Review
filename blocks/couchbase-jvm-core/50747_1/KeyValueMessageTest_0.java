    @Test
    public void foo() {
        env().eventBus().get().subscribe(new Action1<CouchbaseEvent>() {
            @Override
            public void call(CouchbaseEvent couchbaseEvent) {
                System.err.println(couchbaseEvent);
            }
        });

        while(true) {
            GetRequest request = new GetRequest("foobar", bucket());
            GetResponse getResponse = cluster().<GetResponse>send(request).toBlocking().single();
            ReferenceCountUtil.releaseLater(getResponse.content());
        }
    }

