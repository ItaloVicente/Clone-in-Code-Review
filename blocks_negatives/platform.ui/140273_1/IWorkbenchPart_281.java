    /**
     * Returns the title tool tip text of this workbench part.
     * An empty string result indicates no tool tip.
     * If this value changes the part must fire a property listener event with
     * <code>PROP_TITLE</code>.
     * <p>
     * The tool tip text is used to populate the title bar of this part's
     * visual container.
     * </p>
     *
     * @return the workbench part title tool tip (not <code>null</code>)
     */
    String getTitleToolTip();
