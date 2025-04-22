	@Override
	public boolean isEnabled() {
		final Repository repo = getRepository();
		return repo != null && containsHead(repo);
	}

