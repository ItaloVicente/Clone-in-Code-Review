	protected void commitPack(Collection<DfsPackDescription> desc
			Collection<DfsPackDescription> replaces) throws IOException {
		commitPackImpl(desc
		getRepository().fireEvent(new DfsPacksChangedEvent());
	}

	protected abstract void commitPackImpl(Collection<DfsPackDescription> desc
