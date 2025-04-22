    private static final String LISTENER_EVENTS = PlatformUI.PLUGIN_ID + "/debug"; //$NON-NLS-1$
    public  static final String PAGE_PARTLISTENER_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchPage.IPartListener"; //$NON-NLS-1$
    public  static final String PAGE_PARTLISTENER2_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchPage.IPartListener2"; //$NON-NLS-1$
    private static final String PAGE_PROPERTY_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchPage.IPropertyChangeListener"; //$NON-NLS-1$
    private static final String WINDOW_PAGE_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchWindow.IPageListener"; //$NON-NLS-1$
    private static final String WINDOW_PERSPECTIVE_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchWindow.IPerspectiveListener"; //$NON-NLS-1$
    public  static final String WINDOW_PARTLISTENER_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchWindow.IPartListener"; //$NON-NLS-1$
    public  static final String WINDOW_PARTLISTENER2_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchWindow.IPartListener2"; //$NON-NLS-1$
    private static final String PARTREFERENCE_PROPERTY_EVENTS = PlatformUI.PLUGIN_ID + "/listeners/IWorkbenchPartReference"; //$NON-NLS-1$

    public static final boolean enabled = internal_isEnabled(LISTENER_EVENTS);
