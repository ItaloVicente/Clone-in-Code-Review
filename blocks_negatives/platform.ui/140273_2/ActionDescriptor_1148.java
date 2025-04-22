    /**
     * Popup constant.  Value <code>0x1</code>.
     */
    public static final int T_POPUP = 0x1;

    /**
     * View constant.  Value <code>0x2</code>.
     */
    public static final int T_VIEW = 0x2;

    /**
     * Workbench constant.  Value <code>0x3</code>.
     */
    public static final int T_WORKBENCH = 0x3;

    /**
     * Editor constant.  Value <code>0x4</code>.
     */
    public static final int T_EDITOR = 0x4;

    /**
     * Workbench pulldown constant.  Value <code>0x5</code>.
     */
    public static final int T_WORKBENCH_PULLDOWN = 0x5;

    /**
     * Push style constant.  Value <code>push</code>.
     */

    /**
     * Radio style constant.  Value <code>radio</code>.
     */

    /***
     * Toggle style constant.  Value <code>toggle</code>.
     */

    /**
     * Pulldown style constant.  Value <code>pulldown</code>.
     */

    /**
     * Creates a new descriptor with the specified target.
     *
     * @param actionElement the configuration element
     * @param targetType the type of action
     */
    public ActionDescriptor(IConfigurationElement actionElement, int targetType) {
        this(actionElement, targetType, null);
    }

    /**
     * Creates a new descriptor with the target and destination workbench part
     * it will go into.
     *
     * @param actionElement the configuration element
     * @param targetType the type of action
     * @param target the target object
     */
    public ActionDescriptor(IConfigurationElement actionElement,
            int targetType, Object target) {
        id = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
