    /**
     * Image registry key of the default image for wizard pages (value
     * <code>"org.eclipse.jface.wizard.Wizard.pageImage"</code>).
     */

    /**
     * The wizard container this wizard belongs to; <code>null</code> if none.
     */
    private IWizardContainer container = null;

    /**
     * This wizard's list of pages (element type: <code>IWizardPage</code>).
     */
    private List<IWizardPage> pages = new ArrayList<>();

    /**
     * Indicates whether this wizard needs a progress monitor.
     */
    private boolean needsProgressMonitor = false;

    /**
     * Indicates whether this wizard needs previous and next buttons even if the
     * wizard has only one page.
     */
    private boolean forcePreviousAndNextButtons = false;

    /**
     * Indicates whether this wizard supports help.
     */
    private boolean isHelpAvailable = false;

    /**
     * The default page image for pages without one of their one;
     * <code>null</code> if none.
     */
    private Image defaultImage = null;

    /**
     * The default page image descriptor, used for creating a default page image
     * if required; <code>null</code> if none.
     */
    private ImageDescriptor defaultImageDescriptor = JFaceResources.getImageRegistry().getDescriptor(DEFAULT_IMAGE);

    /**
     * The color of the wizard title bar; <code>null</code> if none.
     */
    private RGB titleBarColor = null;

    /**
     * The window title string for this wizard; <code>null</code> if none.
     */
    private String windowTitle = null;

    /**
     * The dialog settings for this wizard; <code>null</code> if none.
     */
    private IDialogSettings dialogSettings = null;

    /**
     * Creates a new empty wizard.
     */
    protected Wizard() {
        super();
    }

    /**
     * Adds a new page to this wizard. The page is inserted at the end of the
     * page list.
     *
     * @param page
     *            the new page
     */
    public void addPage(IWizardPage page) {
        pages.add(page);
        page.setWizard(this);
    }
