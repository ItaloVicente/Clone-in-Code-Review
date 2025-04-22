    /**
     * The real <code>IActivity</code>.
     */
    private IActivity activity;

    /**
     * The <code>ICategory</code> under which this proxy will be rendered.
     */
    private ICategory category;

    /**
     * Create a new instance.
     *
     * @param category the <code>ICategory</code> under which this proxy will be
     * rendered.
     * @param activity the real <code>IActivity</code>.
     */
    public CategorizedActivity(ICategory category, IActivity activity) {
        this.activity = activity;
        this.category = category;
    }

    @Override
