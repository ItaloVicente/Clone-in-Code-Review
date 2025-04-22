	@Override
	public void fireOpen(OpenEvent event) {
		super.fireOpen(event);
	}

	public void setActions(Collection<IAction> actions) {
		extraActions = actions;
	}

