                final SubMultiMutationRequest req = new SubMultiMutationRequest(
                    docId,
                    bucketName,
                    expiry,
                    cas,
                    SubMultiMutationDocOptionsBuilder.builder()
                        .upsertDocument(upsertDocument)
                        .insertDocument(insertDocument),
                    commands
                );
                addRequestSpan(environment, req, "subdoc_multi_mutate");
                return applyTimeout(core.<MultiMutationResponse>send(req)
                    .flatMap(new Func1<MultiMutationResponse, Observable<DocumentFragment<Mutation>>>() {
                        @Override
                        public Observable<DocumentFragment<Mutation>> call(MultiMutationResponse response) {
                            if (response.content() != null && response.content().refCnt() > 0) {
                                response.content().release();
