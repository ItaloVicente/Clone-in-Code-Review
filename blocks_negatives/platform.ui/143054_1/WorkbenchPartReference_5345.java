     * Internal property ID: Indicates that the underlying part was created
     */
    public static final int INTERNAL_PROPERTY_OPENED = 0x211;

    /**
     * Internal property ID: Indicates that the underlying part was destroyed
     */
    public static final int INTERNAL_PROPERTY_CLOSED = 0x212;

    /**
     * Internal property ID: Indicates that the result of IEditorReference.isPinned()
     */
    public static final int INTERNAL_PROPERTY_PINNED = 0x213;

    /**
     * Internal property ID: Indicates that the result of getVisible() has changed
     */
    public static final int INTERNAL_PROPERTY_VISIBLE = 0x214;

    /**
     * Internal property ID: Indicates that the result of isZoomed() has changed
     */
    public static final int INTERNAL_PROPERTY_ZOOMED = 0x215;

    /**
     * Internal property ID: Indicates that the part has an active child and the
     * active child has changed. (fired by PartStack)
     */
    public static final int INTERNAL_PROPERTY_ACTIVE_CHILD_CHANGED = 0x216;

    /**
     * Internal property ID: Indicates that changed in the min / max
     * state has changed
     */
    public static final int INTERNAL_PROPERTY_MAXIMIZED = 0x217;


    /**
     * State constant indicating that the part is not created yet
     */
    public static int STATE_LAZY = 0;

    /**
     * State constant indicating that the part is in the process of being created
     */
    public static int STATE_CREATION_IN_PROGRESS = 1;

    /**
     * State constant indicating that the part has been created
     */
    public static int STATE_CREATED = 2;

    /**
     * State constant indicating that the reference has been disposed (the reference shouldn't be
     * used anymore)
     */
    public static int STATE_DISPOSED = 3;
