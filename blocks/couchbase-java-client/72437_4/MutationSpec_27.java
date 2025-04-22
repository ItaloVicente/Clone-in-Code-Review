        this.attributeAccess = false;
    }

    public MutationSpec(Mutation type, String path, Object fragment, SubdocOptionsBuilder builder) {
        this.type = type;
        this.path = path;
        this.fragment = fragment;
        this.createParents = builder.createParents();
        this.attributeAccess = builder.attributeAccess();
    }

    public MutationSpec(Mutation type, String path, Object fragment) {
        this.type = type;
        this.path = path;
        this.fragment = fragment;
        this.createParents = false;
        this.attributeAccess = false;
