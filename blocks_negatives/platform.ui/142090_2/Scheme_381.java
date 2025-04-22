    /**
     * The collection of all objects listening to changes on this scheme. This
     * value is <code>null</code> if there are no listeners.
     */
    private Set listeners = null;

    /**
     * The parent identifier for this scheme. This is the identifier of the
     * scheme from which this scheme inherits some of its bindings. This value
     * can be <code>null</code> if the scheme has no parent.
     */
    private String parentId = null;

    /**
     * Constructs a new instance of <code>Scheme</code> with an identifier.
     *
     * @param id
     *            The identifier to create; must not be <code>null</code>.
     */
    Scheme(final String id) {
        super(id);
    }

    /**
     * Registers an instance of <code>ISchemeListener</code> to listen for
     * changes to attributes of this instance.
     *
     * @param schemeListener
     *            the instance of <code>ISchemeListener</code> to register.
     *            Must not be <code>null</code>. If an attempt is made to
     *            register an instance of <code>ISchemeListener</code> which
     *            is already registered with this instance, no operation is
     *            performed.
     */
    public final void addSchemeListener(final ISchemeListener schemeListener) {
        if (schemeListener == null) {
        }

        if (listeners == null) {
            listeners = new HashSet();
        }

        listeners.add(schemeListener);
    }

    @Override
