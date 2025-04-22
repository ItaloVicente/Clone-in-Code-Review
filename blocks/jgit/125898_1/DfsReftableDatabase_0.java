		RefList<Ref> none = RefList.emptyList();
		return new RefMap(prefix
	}

	@Override
	public List<Ref> getRefsByPrefix(String prefix) throws IOException {
		return getRefs(prefix
	}

	private RefList<Ref> getRefs(String refName
			throws IOException {
