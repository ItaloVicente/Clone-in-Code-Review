        }
        return null;
    }

    /**
     * Returns the <code>IShowInSource</code> for this view.
     */
    protected IShowInSource getShowInSource() {
        return () -> new ShowInContext(getViewer().getInput(), getViewer()
		        .getSelection());
    }

    /**
     * Returns the <code>IShowInTarget</code> for this view.
     */
    protected IShowInTarget getShowInTarget() {
        return context -> {
