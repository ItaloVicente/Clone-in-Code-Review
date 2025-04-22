			Activator.logError(UIText.GitHistoryPage_openFailed, e);
			Activator.showError(UIText.GitHistoryPage_openFailed, null);
		}
	}

	TreeWalk getTreeWalk() {
		if (walker == null)
		return walker;
	}

	@NonNull
	Repository getRepository() {
		Repository repo = db;
		if (repo == null) {
