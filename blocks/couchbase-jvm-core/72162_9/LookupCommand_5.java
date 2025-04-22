    protected LookupCommand(LookupCommandBuilder builder) {
        this.lookup = builder.lookup();
        this.path = builder.path();
        this.attributeAccess = builder.attributeAccess();
    }

