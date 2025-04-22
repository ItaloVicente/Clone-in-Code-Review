		}
		return null;
	}

	protected IShowInSource getShowInSource() {
		return () -> new ShowInContext(getViewer().getInput(), getViewer().getSelection());
	}

	protected IShowInTarget getShowInTarget() {
		return context -> {
