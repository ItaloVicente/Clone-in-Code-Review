
        return core
            .<RemoveResponse>send(request)
            .map(new Func1<RemoveResponse, D>() {
                @Override
                public D call(final RemoveResponse response) {
                    return (D) transcoder.newDocument(document.id(), 0, null, response.cas());
                }
            });
