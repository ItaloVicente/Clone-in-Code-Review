    /**
	 * Returns the presentation factory for this window. The window consults its
	 * presentation factory for the presentation aspects of views, editors,
	 * status lines, and other components of the window.
	 * <p>
	 * If no presentation factory has been set, a default one is returned.
	 * </p>
	 * 
	 * @return the presentation factory used for this window
	 * @deprecated The presentation API is no longer used and has no effect.
	 *             Refer to the platform porting guide for further details.
	 */
	@Deprecated
    public AbstractPresentationFactory getPresentationFactory();

    /**
	 * Sets the presentation factory. The window consults its presentation
	 * factory for the presentation aspects of views, editors, status lines, and
	 * other components of the window.
	 * <p>
	 * This must be called before the window's controls are created, for example
	 * in <code>preWindowOpen</code>.
	 * </p>
	 * 
	 * @param factory
	 *            the presentation factory to use for this window
	 * @deprecated The presentation API is no longer used and has no effect.
	 *             Refer to the platform porting guide for further details.
	 */
	@Deprecated
    public void setPresentationFactory(AbstractPresentationFactory factory);

