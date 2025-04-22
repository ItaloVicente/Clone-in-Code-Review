    /**
     * Returns whether the label would be affected
     * by a change to the given property of the given element.
     * This can be used to optimize a non-structural viewer update.
     * If the property mentioned in the update does not affect the label,
     * then the viewer need not update the label.
     *
     * @param element the element
     * @param property the property
     * @return <code>true</code> if the label would be affected,
     *    and <code>false</code> if it would be unaffected
     */
    public boolean isLabelProperty(Object element, String property);
