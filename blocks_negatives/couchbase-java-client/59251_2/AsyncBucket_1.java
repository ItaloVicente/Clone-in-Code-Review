    /**
     * Perform several {@link Mutation mutation} operations inside a single existing {@link JsonDocument JSON document}.
     * The list of mutations and paths to mutate in the JSON is represented through {@link MutationSpec MutationSpecs}.
     *
     * Multi-mutations are applied as a whole, atomically at the document level. That means that if one of the mutations
     * fails, none of the mutations are applied. Otherwise, all mutations can be considered successful and the whole
     * operation will receive a {@link MultiMutationResult} with the updated cas (and optionally {@link MutationToken}).
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you want to mutate
     * on the wire, instead of the whole document.
     *
     * This Observable most notable error conditions are:
     *
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *  - The provided CAS doesn't match with the one of the enclosing document: {@link CASMismatchException}
     *  - The mutationSpecs vararg is null: {@link NullPointerException}
     *  - The mutationSpecs vararg is omitted/empty: {@link IllegalArgumentException}
     *  - A mutationSpec couldn't be encoded and the whole operation was cancelled: {@link TranscodingException}
     *  - The multi-mutation failed: {@link MultiMutationException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}

     * When receiving a {@link MultiMutationException}, one can inspect the exception to find the zero-based index and
     * error {@link ResponseStatus status code} of the first failing {@link MutationSpec}. Subsequent mutations may have
     * also failed had they been attempted, but a single spec failing causes the whole operation to be cancelled.
     *
     * Other top-level error conditions are similar to those encountered during a document-level {@link #replace(Document)}.
     *
     * @param doc a {@link JsonDocument} to mutate. Only the {@link JsonDocument#id() id}, {@link JsonDocument#cas() cas}
     *            and {@link JsonDocument#mutationToken()} are used.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param mutationSpecs the list of {@link MutationSpec} to apply to the target document.
     * @return an {@link Observable} of a single {@link MultiMutationResult} (if successful) containing updated cas metadata.
     */
    Observable<MultiMutationResult> mutateIn(JsonDocument doc, PersistTo persistTo, ReplicateTo replicateTo, MutationSpec... mutationSpecs);

    /**
     * Perform several {@link Mutation mutation} operations inside a single existing {@link JsonDocument JSON document}.
     * The list of mutations and paths to mutate in the JSON is represented through {@link MutationSpec MutationSpecs}.
     *
     * Multi-mutations are applied as a whole, atomically at the document level. That means that if one of the mutations
     * fails, none of the mutations are applied. Otherwise, all mutations can be considered successful and the whole
     * operation will receive a {@link MultiMutationResult} with the updated cas (and optionally {@link MutationToken}).
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you want to mutate
     * on the wire, instead of the whole document.
     *
     * This Observable most notable error conditions are:
     *
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *  - The mutationSpecs vararg is null: {@link NullPointerException}
     *  - The mutationSpecs vararg is omitted/empty: {@link IllegalArgumentException}
     *  - A mutationSpec couldn't be encoded and the whole operation was cancelled: {@link TranscodingException}
     *  - The multi-mutation failed: {@link MultiMutationException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}

     *
     * When receiving a {@link MultiMutationException}, one can inspect the exception to find the zero-based index and
     * error {@link ResponseStatus status code} of the first failing {@link MutationSpec}. Subsequent mutations may have
     * also failed had they been attempted, but a single spec failing causes the whole operation to be cancelled.
     *
     * Other top-level error conditions are similar to those encountered during a document-level {@link #replace(Document)}.
     *
     * @param docId the id of {@link JsonDocument} to mutate. Use the
     *              {@link #mutateIn(JsonDocument, PersistTo, ReplicateTo, MutationSpec[]) JsonDocument-based variant}
     *              if you want to use optimistic locking via cas, or modify the document's expiry.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param mutationSpecs the list of {@link MutationSpec} to apply to the target document.
     * @return an {@link Observable} of a single {@link MultiMutationResult} (if successful) containing updated cas metadata.
     */
    Observable<MultiMutationResult> mutateIn(String docId, PersistTo persistTo, ReplicateTo replicateTo,
            MutationSpec... mutationSpecs);
