    /**
     * Constructor argument value that indicates to sort items by name.
     */
    public static final int NAME = 1;

    /**
     * Constructor argument value that indicates to sort items by extension.
     */
    public static final int TYPE = 2;

    private int criteria;

    /**
     * Creates a resource sorter that will use the given sort criteria.
     *
     * @param criteria the sort criterion to use: one of <code>NAME</code> or
     *   <code>TYPE</code>
     */
    public ResourceComparator(int criteria) {
        super();
        this.criteria = criteria;
    }

    /**
     * Returns an integer value representing the relative sort priority of the
     * given element based on its class.
     * <p>
     * <ul>
     *   <li>resources (<code>IResource</code>) - 2</li>
     *   <li>project references (<code>ProjectReference</code>) - 1</li>
     *   <li>everything else - 0</li>
     * </ul>
     * </p>
     *
     * @param element the element
     * @return the sort priority (larger numbers means more important)
     */
    protected int classComparison(Object element) {
        if (element instanceof IResource) {
            return 2;
        }
        return 0;
    }

    @Override
