    /**
     * Modifies the value for the given property of the given element.
     * Has no effect if the element does not have the given property,
     * or if the property cannot be modified.
     * <p>
     * Note that it is possible for an SWT Item to be passed instead of
     * the model element. To handle this case in a safe way, use:
     * <pre>
     *     if (element instanceof Item) {
     *         element = ((Item) element).getData();
     *     }
     * </pre>
     * </p>
     *
     * @param element the model element or SWT Item (see above)
     * @param property the property
     * @param value the new property value
     *
     * @see org.eclipse.swt.widgets.Item
     */
    public void modify(Object element, String property, Object value);
