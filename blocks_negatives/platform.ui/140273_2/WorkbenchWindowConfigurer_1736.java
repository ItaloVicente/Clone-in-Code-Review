public final class WorkbenchWindowConfigurer implements
        IWorkbenchWindowConfigurer {

    /**
     * The workbench window associated with this configurer.
     */
    private WorkbenchWindow window;

    /**
     * The shell style bits to use when the window's shell is being created.
     */
    private int shellStyle = SWT.SHELL_TRIM | Window.getDefaultOrientation();

    /**
     * The window title to set when the window's shell has been created.
     */
    private String windowTitle;

    /**
     * Whether the workbench window should show the perspective bar
     */
    private boolean showPerspectiveBar = false;

    /**
     * Whether the workbench window should show the status line.
     */
    private boolean showStatusLine = true;

    /**
     * Whether the workbench window should show the main tool bar.
     */
    private boolean showToolBar = true;

    /**
     * Whether the workbench window should show the main menu bar.
     */
    private boolean showMenuBar = true;

    /**
     * Whether the workbench window should have a progress indicator.
     */
    private boolean showProgressIndicator = false;

    /**
     * Table to hold arbitrary key-data settings (key type: <code>String</code>,
     * value type: <code>Object</code>).
     * @see #setData
     */
    private Map extraData = new HashMap(1);

    /**
     * Holds the list drag and drop <code>Transfer</code> for the
     * editor area
     */
    private ArrayList transferTypes = new ArrayList(3);

    /**
     * The <code>DropTargetListener</code> implementation for handling a
     * drop into the editor area.
     */
    private DropTargetListener dropTargetListener = null;

    /**
     * Object for configuring this workbench window's action bars.
     * Lazily initialized to an instance unique to this window.
     */
    private WindowActionBarConfigurer actionBarConfigurer = null;

    /**
     * The initial size to use for the shell.
     */
    private Point initialSize = new Point(1024, 768);

    /**
     * Action bar configurer that changes this workbench window.
     * This implementation keeps track of of cool bar items
     */
    class WindowActionBarConfigurer implements IActionBarConfigurer2 {

        private IActionBarConfigurer2 proxy;

        /**
         * Sets the proxy to use, or <code>null</code> for none.
         *
         * @param proxy the proxy
         */
        public void setProxy(IActionBarConfigurer2 proxy) {
            this.proxy = proxy;
        }

        @Override
