                    public void call(Map<Integer, MutationToken> collector, GetAllMutationTokensResponse response) {
                        for (MutationToken token : response.mutationTokens()) {
                            int key = (int) token.vbucketID();
                            MutationToken prev = collector.get(key);
                            MutationToken current = token;
                            if (prev != null && prev.sequenceNumber() != token.sequenceNumber()) {
                                if (current.sequenceNumber() < prev.sequenceNumber()) {
                                    current = prev;
                                }
                                LOGGER.debug("nodes are not agree on sequence number for vbucket {}: old={}, new={}, selected={}",
                                        token.vbucketID(), prev.sequenceNumber(), token.sequenceNumber(), current.sequenceNumber());
                            }
                            collector.put(key, current);
                        }
