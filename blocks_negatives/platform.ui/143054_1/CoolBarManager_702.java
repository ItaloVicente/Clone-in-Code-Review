public class CoolBarManager extends ContributionManager implements
        ICoolBarManager {

    /**
     * A separator created by the end user.
     */

    /**
     * The original creation order of the contribution items.
     */
    private ArrayList<IContributionItem> cbItemsCreationOrder = new ArrayList<>();

    /**
     * MenuManager for cool bar pop-up menu, or null if none.
     */
    private MenuManager contextMenuManager = null;

    /**
     * The cool bar control; <code>null</code> before creation and after
     * disposal.
     */
    private CoolBar coolBar = null;

    /**
     * The cool bar items style; <code>SWT.NONE</code> by default.
     */
    private int itemStyle = SWT.NONE;

    /**
     * Creates a new cool bar manager with the default style. Equivalent to
     * <code>CoolBarManager(SWT.NONE)</code>.
     */
    public CoolBarManager() {
    }

    /**
     * Creates a cool bar manager for an existing cool bar control. This
     * manager becomes responsible for the control, and will dispose of it when
     * the manager is disposed.
     *
     * @param coolBar
     *            the cool bar control
     */
    public CoolBarManager(CoolBar coolBar) {
        this();
        Assert.isNotNull(coolBar);
        this.coolBar = coolBar;
        itemStyle = coolBar.getStyle();
    }

    /**
     * Creates a cool bar manager with the given SWT style. Calling <code>createControl</code>
     * will create the cool bar control.
     *
     * @param style
     *            the cool bar item style; see
     *            {@link org.eclipse.swt.widgets.CoolBar CoolBar}for for valid
     *            style bits
     */
    public CoolBarManager(int style) {
        itemStyle = style;
    }

    @Override
