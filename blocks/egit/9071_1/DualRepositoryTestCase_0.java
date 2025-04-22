		if (repository1 != null)
			repository1.dispose();
		if (repository2 != null)
			repository2.dispose();
		if (testProject != null)
			testProject.delete(false, false, null);
		testUtils.deleteTempDirs();
