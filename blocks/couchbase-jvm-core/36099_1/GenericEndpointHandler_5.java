    private static final EventTranslatorOneArg<ResponseEvent, CouchbaseResponse> RESPONSE_TRANSLATOR =
        new EventTranslatorOneArg<ResponseEvent, CouchbaseResponse>() {
            @Override
            public void translateTo(ResponseEvent event, long sequence, CouchbaseResponse response) {
                event.setResponse(response);
            }
        };

