                return Observable.from(commands);
            }
        }).toList()
        .flatMap(new Func1<List<MutationCommand>, Observable<MultiMutationResponse>>(){
            @Override
            public Observable<MultiMutationResponse> call(List<MutationCommand> mutationCommands) {
                return core.send(new SubMultiMutationRequest(docId, bucketName,
                        expiry, cas, SubMultiMutationDocOptionsBuilder.builder().upsertDocument(upsertDocument).insertDocument(insertDocument),
                        mutationCommands));
            }
        }).flatMap(new Func1<MultiMutationResponse, Observable<DocumentFragment<Mutation>>>() {
            @Override
            public Observable<DocumentFragment<Mutation>> call(MultiMutationResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }
