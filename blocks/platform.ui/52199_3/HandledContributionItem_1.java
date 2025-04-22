	@Override
	public void setModel(MItem item) {
		if (!(item instanceof MHandledItem)) {
			throw new IllegalArgumentException("Only instances of MHandledItem are allowed"); //$NON-NLS-1$
		}

		super.setModel(item);

