    @InterfaceStability.Experimental
    public AsyncMutateInBuilder upsert(JsonObject content) {
        this.mutationSpecs.add(new MutationSpec(Mutation.UPSERTDOC, "", content));
        return this;
    }

