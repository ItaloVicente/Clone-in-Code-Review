		try {
			currentWalk.addAdditionalRefs(db);
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorReadingAdditionalRefs , Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db)), e);
		}
