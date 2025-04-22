        final CoreDocument document = new CoreDocument(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, 0, false, null);

        final ReplaceRequest insert = new ReplaceRequest(document, bucket());
        final ReplaceResponse firstResponse = cluster().<ReplaceResponse>send(insert).toBlocking().single();
        assertEquals(ResponseStatus.NOT_EXISTS, firstResponse.status());

        final UpsertRequest upsert = new UpsertRequest(firstResponse.document(), bucket());
        final ReplaceResponse secondResponse = cluster().<UpsertResponse>send(upsert)
                .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                    @Override
                    public Observable<ReplaceResponse> call(final UpsertResponse response) {
                        final CoreDocument replaceDocument = new CoreDocument(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, response.cas(), false, null);
                        return cluster().send(new ReplaceRequest(replaceDocument, bucket()));
                    }
                }).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, secondResponse.status());
