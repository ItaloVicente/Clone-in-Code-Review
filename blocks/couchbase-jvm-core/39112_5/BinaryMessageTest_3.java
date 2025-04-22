        final CoreDocument document = new CoreDocument(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, 0, false, null);

        final ReplaceRequest insert = new ReplaceRequest(document, bucket());
        final ReplaceResponse firstReplace = cluster().<ReplaceResponse>send(insert).toBlocking().single();
        assertEquals(ResponseStatus.NOT_EXISTS, firstReplace.status());

        final UpsertRequest upsert = new UpsertRequest(firstReplace.document(), bucket());
        final ReplaceResponse secondReplace = cluster().<UpsertResponse>send(upsert)
                .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                    @Override
                    public Observable<ReplaceResponse> call(UpsertResponse response) {
                        final CoreDocument upsertDocument = new CoreDocument(
                                response.document().id(),
                                response.document().content(),
                                response.document().flags(),
                                response.document().expiration(),
                                24234234L,
                                response.document().isJson(),
                                response.status()
                        );
                        return cluster().send(new ReplaceRequest(upsertDocument, bucket()));
                    }
                }).toBlocking().single();
        assertEquals(ResponseStatus.EXISTS, secondReplace.status());
