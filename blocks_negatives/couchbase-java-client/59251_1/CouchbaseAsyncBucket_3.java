    @Override
    public Observable<MultiMutationResult> mutateIn(final JsonDocument doc, PersistTo persistTo, ReplicateTo replicateTo,
            final MutationSpec... mutationSpecs) {
        return mutateIn(doc.id(), doc.cas(), doc.expiry(), persistTo, replicateTo, mutationSpecs);
    }

    @Override
    public Observable<MultiMutationResult> mutateIn(String docId, PersistTo persistTo, ReplicateTo replicateTo,
            final MutationSpec... mutationSpecs) {
        return mutateIn(docId, 0L, 0, persistTo, replicateTo, mutationSpecs);
    }

    protected Observable<MultiMutationResult> mutateIn(final String docId, final long cas, final int expiry,
            final PersistTo persistTo, final ReplicateTo replicateTo, final MutationSpec... mutationSpecs) {
        if (mutationSpecs == null) {
            throw new NullPointerException("At least one MutationSpec is necessary for mutateIn");
        }
        if (mutationSpecs.length == 0) {
            throw new IllegalArgumentException("At least one MutationSpec is necessary for mutateIn");
        }

        Observable<MultiMutationResult> mutations = Observable.defer(new Func0<Observable<MutationCommand>>() {
            @Override
            public Observable<MutationCommand> call() {
                List<ByteBuf> bufList = new ArrayList<ByteBuf>(mutationSpecs.length);
                final List<MutationCommand> commands = new ArrayList<MutationCommand>(mutationSpecs.length);

                for (int i = 0; i < mutationSpecs.length; i++) {
                    MutationSpec spec = mutationSpecs[i];
                    if (spec.type() == Mutation.DELETE) {
                        commands.add(new MutationCommand(Mutation.DELETE, spec.path()));
                    } else {
                        try {
                            ByteBuf buf = subdocumentTranscoder.encodeWithMessage(spec.fragment(), "Couldn't encode MutationSpec #" +
                                    i + " (" + spec.type() + " on " + spec.path() + ") in " + docId);
                            bufList.add(buf);
                            commands.add(new MutationCommand(spec.type(), spec.path(), buf, spec.createParents()));
                        } catch (TranscodingException e) {
                            releaseAll(bufList);
                            return Observable.error(e);
                        }
                    }
                }
                return Observable.from(commands);
            }
        }).toList()
        .flatMap(new Func1<List<MutationCommand>, Observable<MultiMutationResponse>>(){
            @Override
            public Observable<MultiMutationResponse> call(List<MutationCommand> mutationCommands) {
                return core.send(new SubMultiMutationRequest(docId, bucket, expiry, cas, mutationCommands));
            }
        }).flatMap(new Func1<MultiMutationResponse, Observable<MultiMutationResult>>() {
            @Override
            public Observable<MultiMutationResult> call(MultiMutationResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return Observable.just(
                            new MultiMutationResult(docId, response.cas(), response.mutationToken()));
                }

                switch(response.status()) {
                    case SUBDOC_MULTI_PATH_FAILURE:
                        int index = response.firstErrorIndex();
                        ResponseStatus errorStatus = response.firstErrorStatus();
                        String errorPath = mutationSpecs[index].path();
                        CouchbaseException errorException = commonSubdocErrors(errorStatus, docId, errorPath);

                        return Observable.error(new MultiMutationException(index, errorStatus,
                                Arrays.asList(mutationSpecs), errorException));
                    default:
                        return Observable.error(commonSubdocErrors(response.status(), docId, "MULTI-MUTATION"));
                }
            }
        });

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return mutations;
        }

        return mutations.flatMap(new Func1<MultiMutationResult, Observable<MultiMutationResult>>() {
            @Override
            public Observable<MultiMutationResult> call(final MultiMutationResult result) {
                return Observe
                        .call(core, bucket, result.id(), result.cas(), false, result.mutationToken(),
                                persistTo.value(), replicateTo.value(),
                                environment.observeIntervalDelay(), environment.retryStrategy())
                        .map(new Func1<Boolean, MultiMutationResult>() {
                            @Override
                            public MultiMutationResult call(Boolean aBoolean) {
                                return result;
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<MultiMutationResult>>() {
                            @Override
                            public Observable<MultiMutationResult> call(Throwable throwable) {
                                return Observable.error(new DurabilityException(
                                        "Durability requirement failed: " + throwable.getMessage(),
                                        throwable));
                            }
                        });
            }
        });
    }
