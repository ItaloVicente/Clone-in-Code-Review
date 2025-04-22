			String errorMessage = NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, db.getDirectory()
							.getAbsolutePath());
			setErrorMessage(errorMessage);
			return false;
		}

		if (headId == null) {
			String errorMessage = NLS.bind(
					UIText.GitHistoryPage_errorParsingHead, Activator
							.getDefault().getRepositoryUtil()
							.getRepositoryName(db));
			setErrorMessage(errorMessage);
