    @Test
    public void shouldQuery() throws Exception {
        cluster().<GenericQueryResponse>send(new GenericQueryRequest("SELECT * FROM beer-sample LIMI 4",
            bucket(), password())).toBlocking().forEach(new Action1<GenericQueryResponse>() {
            @Override
            public void call(GenericQueryResponse genericQueryResponse) {
                    genericQueryResponse.rows().toBlocking().forEach(new Action1<ByteBuf>() {
                        @Override
                        public void call(ByteBuf buf) {
                        }
                    });
            }
        });
    }

