	/**
	 * @return the HEAD Ref
	 */
	public Ref getHead() {
		try {
			Ref h = repo.exactRef(Constants.HEAD);
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

