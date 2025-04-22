     * The base implementation of this {@link org.eclipse.ui.intro.IIntroPart}method ignores the
     * memento and initializes the part in a fresh state. Subclasses may extend
     * to perform any state restoration, but must call the super method.
     *
     * @param site
     *            the intro site
     * @param memento
     *            the intro part state or <code>null</code> if there is no
     *            previous saved state
     * @exception PartInitException
     *                if this part was not initialized successfully
     */
    @Override
	public void init(IIntroSite site, IMemento memento)
            throws PartInitException {
        setSite(site);
    }

    /**
     * Sets the part site.
     * <p>
     * Subclasses must invoke this method from {@link org.eclipse.ui.intro.IIntroPart#init(IIntroSite, IMemento)}.
     * </p>
     *
     * @param site the intro part site
     */
    protected void setSite(IIntroSite site) {
        this.partSite = site;
    }

    @Override
