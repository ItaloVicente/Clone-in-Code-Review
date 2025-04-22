	private void updateVisible() {
		setVisible((model).isVisible());
		final IContributionManager parent = getParent();
		if (parent != null) {
			parent.markDirty();
		}
	}

	@Override
	public void update() {
		update(null);
	}

