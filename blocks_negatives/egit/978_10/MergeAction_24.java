
	@Override
	public boolean isEnabled() {
		boolean enabled = true;
		Repository repository = getRepository(false);
		if (repository == null)
			enabled = false;
		return enabled;
	}

