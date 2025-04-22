    private QName name;
    private String id;

    private static final long serialVersionUID = 2193425983220679887L;

    /**
     * Create a new NullPointer.
     * @param name node name
     * @param locale Locale
     */
    public NullPointer(QName name, Locale locale) {
        super(null, locale);
        this.name = name;
    }

    /**
     * Used for the root node.
     * @param parent parent pointer
     * @param name node name
     */
    public NullPointer(NodePointer parent, QName name) {
        super(parent);
        this.name = name;
    }

    /**
     * Create a new NullPointer.
     * @param locale Locale
     * @param id String
     */
    public NullPointer(Locale locale, String id) {
        super(null, locale);
        this.id = id;
    }

    @Override
