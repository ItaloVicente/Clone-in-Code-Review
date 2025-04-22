		checkPacks(pm
		checkConnectivity(pm
		return errors;
	}

	private void checkPacks(ProgressMonitor pm
			throws IOException
		try (DfsReader ctx = objdb.newReader()) {
