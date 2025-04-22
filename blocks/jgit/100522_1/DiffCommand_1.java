	private void runExternalDiffDriver(List<DiffEntry> entries
			DiffFormatter diffFmt) throws GitAPIException
		ExternalDiffDriver edd = new ExternalDiffDriver(repo
		for (DiffEntry entry : entries) {
			edd.format(entry);
		}
		edd.flush();
	}

	private boolean useExternalDiffDriver() {
		DiffConfig diffConfig = repo.getConfig().get(DiffConfig.KEY);
		return diffConfig.getExternal() != null;
	}

