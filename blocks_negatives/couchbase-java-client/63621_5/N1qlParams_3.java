    private void storeToken(MutationToken token) {
        if (token == null) {
            throw new IllegalArgumentException("No MutationToken provided (must be enabled on the Environment).");
        }

        if (mutationTokens == null) {
            mutationTokens = new ArrayList<MutationToken>();
            mutationTokens.add(token);
            return;
        }

        Iterator<MutationToken> tokenIterator = mutationTokens.iterator();
        while(tokenIterator.hasNext()) {
            MutationToken t = tokenIterator.next();
            if (t.vbucketID() == token.vbucketID() && t.bucket().equals(token.bucket())) {
                if (token.sequenceNumber() > t.sequenceNumber()) {
                    tokenIterator.remove();
                    mutationTokens.add(token);
                }
                return;
            }
        }

        mutationTokens.add(token);
