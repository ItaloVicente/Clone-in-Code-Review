    /**
     * Creates a GridLayoutFactory that creates GridLayouts with the default SWT
     * values.
     *
     * <p>
     * Initial values are:
     * </p>
     *
     * <ul>
     * <li>numColumns(1)</li>
     * <li>margins(5,5)</li>
     * <li>extendedMargins(0,0,0,0)</li>
     * <li>spacing(5,5)</li>
     * <li>equalWidth(false)</li>
     * </ul>
     *
     * @return a GridLayoutFactory that creates GridLayouts as though created with
     * their default constructor
     * @see #fillDefaults
     */
    public static GridLayoutFactory swtDefaults() {
    	return new GridLayoutFactory(new GridLayout());
    }
