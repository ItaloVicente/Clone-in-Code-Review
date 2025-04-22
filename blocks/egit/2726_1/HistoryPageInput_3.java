
	public Ref getHead() {
		try {
			Ref h = repo.getRef(Constants.HEAD);
			if (h != null && h.isSymbolic())
				return h;
			return null;
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(repo)), e);
		}
	}
