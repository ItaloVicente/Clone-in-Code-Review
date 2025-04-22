    /**
     * The collection of all objects listening to changes on this context. This
     * value is <code>null</code> if there are no listeners.
     */
    private Set<IContextListener> listeners;

    /**
     * The parent identifier for this context. The meaning of a parent is
     * dependent on the system using contexts. This value can be
     * <code>null</code> if the context has no parent.
     */
    private String parentId;

    /**
     * Constructs a new instance of <code>Context</code>.
     *
     * @param id
     *            The id for this context; must not be <code>null</code>.
     */
    Context(final String id) {
        super(id);
    }

    /**
     * Registers an instance of <code>IContextListener</code> to listen for
     * changes to properties of this instance.
     *
     * @param listener
     *            the instance to register. Must not be <code>null</code>. If
     *            an attempt is made to register an instance which is already
     *            registered with this instance, no operation is performed.
     */
    public final void addContextListener(final IContextListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }

        if (listeners == null) {
            listeners = new HashSet<>();
        }

        listeners.add(listener);
    }

    @Override
