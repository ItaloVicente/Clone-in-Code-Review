    /**
     * Returns the title of this intro part. If this value changes
     * the part must fire a property listener event with
     * {@link IIntroPart#PROP_TITLE}.
     * <p>
     * The title is used to populate the title bar of this part's visual
     * container.
     * </p>
     *
     * @return the intro part title (not <code>null</code>)
     * @since 3.2
     */
    String getTitle();
