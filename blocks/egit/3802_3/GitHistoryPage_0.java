		try {
			currentWalk.addAdditionalRefs(db.getRefDatabase().getAdditionalRefs());
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorReadingAdditionalRefs, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)), e);
		}
