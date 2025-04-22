    @Override
    public long mutationCas() {
        if (cas == null) {
            throw new IllegalStateException("Mutation CAS not available");
        }
        return cas;
