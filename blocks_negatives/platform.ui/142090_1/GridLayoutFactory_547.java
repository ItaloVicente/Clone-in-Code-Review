    /**
     * Creates a GridLayoutFactory that creates GridLayouts with no margins and
     * default dialog spacing.
     *
     * <p>
     * Initial values are:
     * </p>
     *
     * <ul>
     * <li>numColumns(1)</li>
     * <li>margins(0,0)</li>
     * <li>extendedMargins(0,0,0,0)</li>
     * <li>spacing(LayoutConstants.getSpacing())</li>
     * <li>equalWidth(false)</li>
     * </ul>
     *
     * @return a GridLayoutFactory that creates GridLayouts as though created with
     * their default constructor
     * @see #swtDefaults
     */
    public static GridLayoutFactory fillDefaults() {
    	GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Point defaultSpacing = LayoutConstants.getSpacing();
        layout.horizontalSpacing = defaultSpacing.x;
        layout.verticalSpacing = defaultSpacing.y;
        return new GridLayoutFactory(layout);
    }
