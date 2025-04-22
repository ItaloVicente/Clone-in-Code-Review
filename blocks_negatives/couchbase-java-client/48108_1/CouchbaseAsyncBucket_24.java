        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
        return core
            .<AppendResponse>send(new AppendRequest(document.id(), document.cas(), encoded.value1(), bucket))
            .map(new Func1<AppendResponse, D>() {
                @Override
                public D call(final AppendResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
