
		if (!System.getProperties().contains("test-repo-no-cleanup"))
			FileUtils.delete(trash, FileUtils.RECURSIVE | FileUtils.RETRY);
	}

}
