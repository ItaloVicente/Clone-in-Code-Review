
	public void dispose() {
		if (projectMapping != null)
			projectMapping.clear();

		if (gsdSet != null)
			for (GitSynchronizeData gsd : gsdSet)
				gsd.dispose();

	}

