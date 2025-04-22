    public SearchQuery scanConsistency(ScanConsistency consistency) {
        this.consistency = consistency;
        this.mutationState = null;
        return this;
    }

    public SearchQuery consistentWith(Document... docs) {
        this.consistency = null;
        this.mutationState = MutationState.from(docs);
        return this;
    }

    public SearchQuery consistentWith(DocumentFragment... fragments) {
        this.consistency = null;
        this.mutationState = MutationState.from(fragments);
        return this;
    }

    public SearchQuery consistentWith(MutationState mutationState) {
        this.consistency = null;
        this.mutationState = mutationState;
        return this;
    }

