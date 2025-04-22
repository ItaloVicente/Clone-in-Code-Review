        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
        return core
            .<PrependResponse>send(new PrependRequest(document.id(), document.cas(), encoded.value1(), bucket))
            .map(new Func1<PrependResponse, D>() {
                @Override
                public D call(final PrependResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
