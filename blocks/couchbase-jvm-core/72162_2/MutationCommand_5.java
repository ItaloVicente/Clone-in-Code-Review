        if(!attributeAccess && expandMacros) {
           throw new IllegalArgumentException("Macros can be used only with extended attributes");
        }
        this.attributeAccess = attributeAccess;
        this.expandMacros = expandMacros;
    }

    public MutationCommand(Mutation mutation, String path, ByteBuf fragment, boolean createIntermediaryPath, boolean attributeAccess) {
        this(mutation, path, fragment, createIntermediaryPath, attributeAccess, false);
    }

    public MutationCommand(Mutation mutation, String path, ByteBuf fragment, boolean createIntermediaryPath) {
        this(mutation, path, fragment, createIntermediaryPath, false, false);
