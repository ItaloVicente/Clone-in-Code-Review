		if (Window.OK != dialog.open())
			return;
		Object[] results = dialog.getResult();
		if (results == null || results.length == 0)
			return;
		for (Object result : results)
			CommitEditor.openQuiet((RepositoryCommit) result);
