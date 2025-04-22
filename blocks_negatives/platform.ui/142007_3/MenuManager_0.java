    /**
     * The menu control; <code>null</code> before
     * creation and after disposal.
     */
    private Menu menu = null;

    /**
     * The menu item widget; <code>null</code> before
     * creation and after disposal. This field is used
     * when this menu manager is a sub-menu.
     */
    private MenuItem menuItem;

    /**
     * The text for a sub-menu.
     */
    private String menuText;

    /**
     * The image for a sub-menu.
     */
    private ImageDescriptor image;

    /**
     * A resource manager to remember all of the images that have been used by this menu.
     */
    private LocalResourceManager imageManager;

    /**
     * The overrides for items of this manager
     */
    private IContributionManagerOverrides overrides;

    /**
     * The parent contribution manager.
     */
    private IContributionManager parent;

    /**
     * Indicates whether <code>removeAll</code> should be
     * called just before the menu is displayed.
     */
    private boolean removeAllWhenShown = false;

    /**
     * Indicates this item is visible in its manager; <code>true</code>
     * by default.
     * @since 3.3
     */
    protected boolean visible = true;
