    protected MutationCommand(MutationCommandBuilder builder) {
        this.mutation = builder.mutation();
        this.path = builder.path();
        this.fragment = builder.fragment();
        this.createIntermediaryPath = builder.createIntermediaryPath();
        this.attributeAccess = builder.attributeAccess();
    }

