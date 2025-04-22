    @InterfaceStability.Experimental
    public N1qlParams scanTokens(MutationToken... mutationTokens) {
        this.mutationTokens = mutationTokens;
        return this;
    }

