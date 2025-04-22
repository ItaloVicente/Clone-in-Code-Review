		return wizard;
	}

	protected boolean isCurrentPage() {
		return (getContainer() != null && this == getContainer()
				.getCurrentPage());
	}

	@Override
