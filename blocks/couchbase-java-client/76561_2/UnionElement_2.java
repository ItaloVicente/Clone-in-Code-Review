    private final String with;

    public UnionElement(final boolean all) {
        this.all = all;
        this.with = null;
    }

    public UnionElement(final boolean all, final String with) {
        this.all = all;
        this.with = with;
    }
