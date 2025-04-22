		final Ref ref = getRef(event);
		return execute(repository, ref);
	}

	public Object execute(final Repository repository, Ref ref)
			throws ExecutionException {
