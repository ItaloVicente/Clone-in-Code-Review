                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                if (response.status().isSuccess()) {
                    try {
                        byte[] raw = null;
                        if (isIncludeRaw()) {
                            TranscoderUtils.ByteBufToArray rawData = TranscoderUtils.byteBufToByteArray(response.content());
                            raw = Arrays.copyOfRange(rawData.byteArray, rawData.offset, rawData.offset + rawData.length);
                        }
                        T content = subdocumentTranscoder.decodeWithMessage(response.content(), fragmentType,
                                "Couldn't decode subget fragment for " + id + "/" + spec.path());
                        SubdocOperationResult<Lookup> single = SubdocOperationResult
                                .createResult(spec.path(), Lookup.GET, response.status(), content, raw);
                        return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(),
                                Collections.singletonList(single));
                    } finally {
                        if (response.content() != null) {
                            response.content().release();
                        }
                    }
                } else {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
