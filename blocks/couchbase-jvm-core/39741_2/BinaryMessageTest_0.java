    @Test
    public void shouldQuery() throws Exception {
        cluster().<GenericQueryResponse>send(new GenericQueryRequest("SELECT * FROM beer-sample LIMIT 1",
            bucket(), password())).toBlocking().forEach(new Action1<GenericQueryResponse>() {
            @Override
            public void call(GenericQueryResponse genericQueryResponse) {
                System.err.println(genericQueryResponse.status());
                System.err.println(genericQueryResponse.content().toString(CharsetUtil.UTF_8));
            }
        });
    }

