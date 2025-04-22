    /**
     * Constant describing the position of the cursor relative
     * to the target object.  This means the mouse is positioned
     * slightly before the target.
     * @see #getCurrentLocation()
     */
    public static final int LOCATION_BEFORE = 1;

    /**
     * Constant describing the position of the cursor relative
     * to the target object.  This means the mouse is positioned
     * slightly after the target.
     * @see #getCurrentLocation()
     */
    public static final int LOCATION_AFTER = 2;

    /**
     * Constant describing the position of the cursor relative
     * to the target object.  This means the mouse is positioned
     * directly on the target.
     * @see #getCurrentLocation()
     */
    public static final int LOCATION_ON = 3;

    /**
     * Constant describing the position of the cursor relative
     * to the target object.  This means the mouse is not positioned
     * over or near any valid target.
     * @see #getCurrentLocation()
     */
    public static final int LOCATION_NONE = 4;

    /**
     * The viewer to which this drop support has been added.
     */
    private Viewer viewer;

    /**
     * The current operation.
     */
    private int currentOperation = DND.DROP_NONE;

    /**
     * The last valid operation.  We need to remember the last good operation
     * in the case where the current operation temporarily is not valid (drag over
     * someplace you can't drop).
     */
    private int lastValidOperation;

    /**
     * This is used because we allow the operation
     * to be temporarily overridden (for example a move to a copy) for a drop that
     * happens immediately after the operation is overridden.
     */
    private int overrideOperation = -1;

    /**
     * The current DropTargetEvent, used only during validateDrop()
     */
    private DropTargetEvent currentEvent;

    /**
     * The data item currently under the mouse.
     */
    private Object currentTarget;

    /**
     * Information about the position of the mouse relative to the
     * target (before, on, or after the target.  Location is one of
     * the <code>LOCATION_* </code> constants defined in this type.
     */
    private int currentLocation;

    /**
     * A flag that allows adapter users to turn the insertion
     * feedback on or off. Default is <code>true</code>.
     */
    private boolean feedbackEnabled = true;

    /**
     * A flag that allows adapter users to turn auto scrolling
     * on or off. Default is <code>true</code>.
     */
    private boolean scrollEnabled = true;

    /**
     * A flag that allows adapter users to turn auto
     * expanding on or off. Default is <code>true</code>.
     */
    private boolean expandEnabled = true;

    /**
     * A flag that allows adapter users to turn selection feedback
     *  on or off. Default is <code>true</code>.
     */
    private boolean selectFeedbackEnabled = true;

    /**
     * Creates a new drop adapter for the given viewer.
     *
     * @param viewer the viewer
     */
    protected ViewerDropAdapter(Viewer viewer) {
        this.viewer = viewer;
    }

	/**
	 * Clears internal state of this drop adapter. This method can be called
	 * when no DnD operation is underway, to clear internal state from previous
	 * drop operations.
