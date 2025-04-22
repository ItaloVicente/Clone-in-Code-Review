        return core
            .<CounterResponse>send(new CounterRequest(id, initial, delta, expiry, bucket))
            .map(new Func1<CounterResponse, JsonLongDocument>() {
                @Override
                public JsonLongDocument call(CounterResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
