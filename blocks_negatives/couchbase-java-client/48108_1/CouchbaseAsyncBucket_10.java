        return core.<ReplaceResponse>send(new ReplaceRequest(document.id(), encoded.value1(), document.cas(), document.expiry(),
            encoded.value2(), bucket))
            .map(new Func1<ReplaceResponse, D>() {
                @Override
                public D call(ReplaceResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
