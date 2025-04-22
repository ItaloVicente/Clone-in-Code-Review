    /**
     * The canonical empty selection. This selection should be used instead of
     * <code>null</code>.
     */
    public static final StructuredSelection EMPTY = new StructuredSelection();

    /**
     * Creates a new empty selection.
     * See also the static field <code>EMPTY</code> which contains an empty selection singleton.
     *
     * @see #EMPTY
     */
    public StructuredSelection() {
    }

    /**
     * Creates a structured selection from the given elements.
     * The given element array must not be <code>null</code>.
     *
     * @param elements an array of elements
     */
    public StructuredSelection(Object[] elements) {
    	Assert.isNotNull(elements);
        this.elements = new Object[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    /**
     * Creates a structured selection containing a single object.
     * The object must not be <code>null</code>.
     *
     * @param element the element
     */
    public StructuredSelection(Object element) {
        Assert.isNotNull(element);
        elements = new Object[] { element };
    }

    /**
     * Creates a structured selection from the given <code>List</code>.
     * @param elements list of selected elements
     */
    public StructuredSelection(List elements) {
    	this(elements, null);
    }

    /**
