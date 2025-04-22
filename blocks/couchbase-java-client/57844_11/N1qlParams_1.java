    @InterfaceStability.Experimental
    public N1qlParams consistentWith(Bucket bucket, Document... documents) {
        if (documents == null || documents.length == 0) {
            throw new UnsupportedOperationException(
                "Bucket level AT_PLUS consistency not yet supported.");
        }

        for (Document doc : documents) {
            storeToken(bucket, doc.id(), doc.mutationToken());
        }

        return this;
    }

    @InterfaceStability.Experimental
    public N1qlParams consistentWith(Bucket bucket, DocumentFragment... fragments) {
        if (fragments == null || fragments.length == 0) {
            throw new UnsupportedOperationException(
                "Bucket level AT_PLUS consistency not yet supported.");
        }

        for (DocumentFragment doc : fragments) {
            storeToken(bucket, doc.id(), doc.mutationToken());
        }

        return this;
    }

    private void storeToken(Bucket bucket, String id, MutationToken token) {
        if (bucket == null) {
            throw new IllegalArgumentException("A valid bucket reference must be provided.");
        }

        if (token == null) {
            throw new UnsupportedOperationException(
                "Document ID fallback for AT_PLUS not yet supported. ID: " + id);
        }

        if (mutationTokens == null) {
            mutationTokens = new HashMap<Bucket, List<MutationToken>>();
        }

        List<MutationToken> tokens = mutationTokens.get(bucket);
        if (tokens == null) {
            tokens = new ArrayList<MutationToken>();
        }

        tokens.add(token);
        mutationTokens.put(bucket, tokens);
    }

