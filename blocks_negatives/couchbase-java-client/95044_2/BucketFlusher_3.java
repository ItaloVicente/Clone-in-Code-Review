        return core
            .<FlushResponse>send(new FlushRequest(bucket, username, password))
            .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
            .map(new Func1<FlushResponse, Boolean>() {
                @Override
                public Boolean call(FlushResponse flushResponse) {
                    if (!flushResponse.status().isSuccess()) {
                        if (flushResponse.content().contains("disabled")) {
                            throw new FlushDisabledException("Flush is disabled for this bucket.");
                        } else {
                            throw new CouchbaseException("Flush failed because of: " + flushResponse.content());
