                    public Observable<MutationToken> call(Map<Integer, MutationToken> sequenceNumbers) {
                        return Observable.from(sequenceNumbers.values());
                    }
                })
                .flatMap(new Func1<MutationToken, Observable<MutationToken>>() {
                    @Override
                    public Observable<MutationToken> call(final MutationToken token) {
                        return core.<GetFailoverLogResponse>send(new GetFailoverLogRequest((short) token.vbucketID(), bucket))
                                .map(new Func1<GetFailoverLogResponse, MutationToken>() {
