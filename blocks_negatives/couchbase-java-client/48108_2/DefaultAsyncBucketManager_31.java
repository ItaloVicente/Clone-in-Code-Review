        RemoveDesignDocumentRequest req = new RemoveDesignDocumentRequest(name, development, bucket, password);
        return core.<RemoveDesignDocumentResponse>send(req)
            .map(new Func1<RemoveDesignDocumentResponse, Boolean>() {
                @Override
                public Boolean call(RemoveDesignDocumentResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
                    return response.status().isSuccess();
