    /**
     * This page's name.
     */
    private String name;

    /**
     * The wizard to which this page belongs; <code>null</code>
     * if this page has yet to be added to a wizard.
     */
    private IWizard wizard = null;

    /**
     * Indicates whether this page is complete.
     */
    private boolean isPageComplete = true;

    /**
     * The page that was shown right before this page became visible;
     * <code>null</code> if none.
     */
    private IWizardPage previousPage = null;

    /**
     * Creates a new wizard page with the given name, and
     * with no title or image.
     *
     * @param pageName the name of the page
     */
    protected WizardPage(String pageName) {
        this(pageName, null, (ImageDescriptor) null);
    }

    /**
     * Creates a new wizard page with the given name, title, and image.
     *
     * @param pageName the name of the page
     * @param title the title for this wizard page,
     *   or <code>null</code> if none
     * @param titleImage the image descriptor for the title of this wizard page,
     *   or <code>null</code> if none
     */
    protected WizardPage(String pageName, String title,
            ImageDescriptor titleImage) {
        super(title, titleImage);
        name = pageName;
    }

    /**
     * The <code>WizardPage</code> implementation of this <code>IWizardPage</code>
     * method returns <code>true</code> if this page is complete (<code>isPageComplete</code>)
     * and there is a next page to flip to. Subclasses may override (extend or reimplement).
     *
     * @see #getNextPage
     * @see #isPageComplete()
     */
    @Override
