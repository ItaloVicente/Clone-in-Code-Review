
    /**
     * Constructs a new Default layout engine.
     */
    public PerspectiveWithFastView() {
        super();
    }

    @Override
	public void createInitialLayout(IPageLayout layout) {
        defineLayout(layout);
    }

    /**
     * Define the initial layout by adding a fast view.
     *
     * @param layout
     *            The page layout.
     */
    public void defineLayout(IPageLayout layout) {
        layout.addFastView("org.eclipse.ui.views.ResourceNavigator", .8f); //$NON-NLS-1$
    }
}
