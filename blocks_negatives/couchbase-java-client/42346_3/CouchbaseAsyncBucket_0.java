        return core.<RemoveResponse>send(request).map(new Func1<RemoveResponse, D>() {
            @Override
            public D call(RemoveResponse response) {
                return (D) transcoder.newDocument(document.id(), document.expiry(), document.content(), document.cas());
            }
        });
