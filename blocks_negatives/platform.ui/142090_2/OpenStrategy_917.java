    /**
     * Default behavior. Double click to open the item.
     */
    public static final int DOUBLE_CLICK = 0;

    /**
     * Single click will open the item.
     */
    public static final int SINGLE_CLICK = 1;

    /**
     * Hover will select the item.
     */
    public static final int SELECT_ON_HOVER = 1 << 1;

    /**
     * Open item when using arrow keys
     */
    public static final int ARROW_KEYS_OPEN = 1 << 2;

    /** A single click will generate
     * an open event but key arrows will not do anything.
     *
     * @deprecated
     */
    @Deprecated
