    /**
     * Returns the full label decorator instance for the specified decorator id
     * if it is enabled. Otherwise returns <code>null</code>. Returns
     * <code>null</code> for lightweight decorators. It is recommended that
     * getBaseLabelProvider is used instead so that lightweight decorators are
     * also checked.
     *
     * @param decoratorId the decorator id
     * @return the label decorator
     */
    ILabelDecorator getLabelDecorator(String decoratorId);
