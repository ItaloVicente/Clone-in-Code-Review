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
