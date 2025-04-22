    @Test
    public void shouldGetDesignDocument() {
        GetDesignDocumentResponse res = cluster()
            .<GetDesignDocumentResponse>send(new GetDesignDocumentRequest("beer", false, bucket(), password()))
            .toBlocking()
            .single();

        System.err.println(res.content().toString(CharsetUtil.UTF_8));
    }

