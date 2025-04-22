    private String fUpperListLabel;

    private String fLowerListLabel;

    /**
     * The comparator used to sort the list in the lower pane.
     * @since 3.5
     */
    private Comparator fLowerListComparator = null;

    private ILabelProvider fQualifierRenderer;

    private Object[] fElements = new Object[0];

    private Table fLowerList;

    private Object[] fQualifierElements;

    /**
     * Creates the two pane element selector.
     *
     * @param parent
     *            the parent shell.
     * @param elementRenderer
     *            the element renderer.
     * @param qualifierRenderer
     *            the qualifier renderer.
     */
    public TwoPaneElementSelector(Shell parent, ILabelProvider elementRenderer,
            ILabelProvider qualifierRenderer) {
        super(parent, elementRenderer);
        setSize(50, 15);
        setAllowDuplicates(false);
        fQualifierRenderer = qualifierRenderer;
    }

    /**
     * Sets the upper list label. If the label is <code>null</code> (default),
     * no label is created.
     *
     * @param label
     */
    public void setUpperListLabel(String label) {
        fUpperListLabel = label;
    }

    /**
     * Sets the lower list label.
     *
     * @param label
     *            String or <code>null</code>. If the label is
     *            <code>null</code> (default), no label is created.
     */
    public void setLowerListLabel(String label) {
        fLowerListLabel = label;
    }

    /**
     * Sets the comparator used to sort the list in the lower pane.
     * <p>
     * Note: the comparator might want to honor
     * {@link AbstractElementListSelectionDialog#isCaseIgnored()}.
     * </p>
     *
     * @param comparator
     *            a Comparator or <code>null</code> if <code>String</code>'s
     *            comparison methods should be used
     * @since 3.5
     */
    public void setLowerListComparator(Comparator comparator) {
    	fLowerListComparator = comparator;
    }

    /**
     * Sets the elements to be displayed.
     *
     * @param elements
     *            the elements to be displayed.
     */
    public void setElements(Object[] elements) {
        fElements = elements;
    }

    /*
     * @see Dialog#createDialogArea(Composite)
     */
    @Override
