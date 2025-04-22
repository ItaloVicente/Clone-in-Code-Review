		if (commit == null)
			return null;
		Repository repo = getRepository(event);
		if (repo == null)
			return null;
		BasicConfigurationDialog.show(repo);
