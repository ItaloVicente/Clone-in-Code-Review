    /**
     * Adds any last-minute pages to this wizard.
     * <p>
     * This method is called just before the wizard becomes visible, to give the
     * wizard the opportunity to add any lazily created pages.
     * </p>
     */
    void addPages();

    /**
     * Returns whether this wizard could be finished without further user
     * interaction.
     * <p>
     * The result of this method is typically used by the wizard container to enable
     * or disable the Finish button.
     * </p>
     *
     * @return <code>true</code> if the wizard could be finished,
     *   and <code>false</code> otherwise
     */
    boolean canFinish();

    /**
     * Creates this wizard's controls in the given parent control.
     * <p>
     * The wizard container calls this method to create the controls
     * for the wizard's pages before the wizard is opened. This allows
     * the wizard to size correctly; otherwise a resize may occur when
     * moving to a new page.
     * </p>
     *
     * @param pageContainer the parent control
     */
    void createPageControls(Composite pageContainer);

    /**
     * Disposes of this wizard and frees all SWT resources.
     */
    void dispose();

    /**
     * Returns the container of this wizard.
     *
     * @return the wizard container, or <code>null</code> if this
     *   wizard has yet to be added to a container
     */
    IWizardContainer getContainer();

    /**
     * Returns the default page image for this wizard.
     * <p>
     * This image can be used for pages which do not
     * supply their own image.
     * </p>
     *
     * @return the default page image
     */
    Image getDefaultPageImage();

    /**
     * Returns the dialog settings for this wizard.
     * <p>
     * The dialog store is used to record state between
     * wizard invocations (for example, radio button selections,
     * last directory, etc.).
     * </p>
     *
     * @return the dialog settings, or <code>null</code> if none
     */
    IDialogSettings getDialogSettings();

    /**
     * Returns the successor of the given page.
     * <p>
     * This method is typically called by a wizard page
     * </p>
     *
     * @param page the page
     * @return the next page, or <code>null</code> if none
     */
    IWizardPage getNextPage(IWizardPage page);

    /**
     * Returns the wizard page with the given name belonging to this wizard.
     *
     * @param pageName the name of the wizard page
     * @return the wizard page with the given name, or <code>null</code> if none
     */
    IWizardPage getPage(String pageName);

    /**
     * Returns the number of pages in this wizard.
     *
     * @return the number of wizard pages
     */
    int getPageCount();

    /**
     * Returns all the pages in this wizard.
     *
     * @return a list of pages
     */
    IWizardPage[] getPages();

    /**
     * Returns the predecessor of the given page.
     * <p>
     * This method is typically called by a wizard page
     * </p>
     *
     * @param page the page
     * @return the previous page, or <code>null</code> if none
     */
    IWizardPage getPreviousPage(IWizardPage page);

    /**
     * Returns the first page to be shown in this wizard.
     *
     * @return the first wizard page
     */
    IWizardPage getStartingPage();

    /**
     * Returns the title bar color for this wizard.
     *
     * @return the title bar color
     */
    RGB getTitleBarColor();

    /**
     * Returns the window title string for this wizard.
     *
     * @return the window title string, or <code>null</code> for no title
     */
    String getWindowTitle();

    /**
