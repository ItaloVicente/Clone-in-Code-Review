	private void enableAuthorText(boolean enabled) {
		if (currentRepository != null
				&& currentRepository.getRepositoryState().equals(
				RepositoryState.CHERRY_PICKING_RESOLVED))
			authorText.setEnabled(false);
		else
			authorText.setEnabled(enabled);
	}

