        final CoreDocument document = new CoreDocument(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, 0, false, null);

        final ReplaceRequest insert = new ReplaceRequest(document, bucket());
        final ReplaceResponse firstReplace = cluster().<ReplaceResponse>send(insert).toBlocking().single();
        assertEquals(ResponseStatus.NOT_EXISTS, firstReplace.status());

        final UpsertRequest upsert = new UpsertRequest(firstReplace.document(), bucket());
        final ReplaceResponse secondReplace = cluster().<UpsertResponse>send(upsert)
                .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                             @Override
                             public Observable<ReplaceResponse> call(UpsertResponse response) {
                                 return cluster().send(new ReplaceRequest(response.document(), bucket()));
                             }
                         }
                ).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, secondReplace.status());
