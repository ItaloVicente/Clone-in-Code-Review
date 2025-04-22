	public RefsChangedEvent(final Repository repository) {
		super(repository);
	}

	@Override
	public String toString() {
		return "RefsChangedEvent[" + getRepository() + "]";
	}
