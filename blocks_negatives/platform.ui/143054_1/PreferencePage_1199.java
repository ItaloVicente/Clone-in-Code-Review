    /**
     * Standard Defaults button, or <code>null</code> if none.
     * This button has id <code>DEFAULTS_ID</code>.
     */
    private Button defaultsButton = null;

    /**
     * The container this preference page belongs to; <code>null</code>
     * if none.
     */
    private IPreferencePageContainer container = null;

    /**
     * Standard Apply button, or <code>null</code> if none.
     * This button has id <code>APPLY_ID</code>.
     */
    private Button applyButton = null;

    /**
     * Description label.
     *
     * @see #createDescriptionLabel(Composite)
     */
    private Label descriptionLabel;

    /**
     * Caches size of page.
     */
    private Point size = null;


    /**
     * Creates a new preference page with an empty title and no image.
     */
    protected PreferencePage() {
    }

    /**
     * Creates a new preference page with the given title and no image.
     *
     * @param title the title of this preference page
     */
    protected PreferencePage(String title) {
        super(title);
    }

    /**
     * Creates a new abstract preference page with the given title and image.
     *
     * @param title the title of this preference page
     * @param image the image for this preference page,
     *  or <code>null</code> if none
     */
    protected PreferencePage(String title, ImageDescriptor image) {
        super(title, image);
    }

    /**
     * Computes the size for this page's UI control.
     * <p>
     * The default implementation of this <code>IPreferencePage</code>
     * method returns the size set by <code>setSize</code>; if no size
     * has been set, but the page has a UI control, the framework
     * method <code>doComputeSize</code> is called to compute the size.
     * </p>
     *
     * @return the size of the preference page encoded as
     *   <code>new Point(width,height)</code>, or
     *   <code>(0,0)</code> if the page doesn't currently have any UI component
     */
    @Override
