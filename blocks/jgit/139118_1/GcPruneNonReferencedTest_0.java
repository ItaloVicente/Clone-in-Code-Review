
	private void assertNoEmptyFanoutDirectories() {
		File[] fanout = repo.getObjectsDirectory().listFiles();
		for (File f : fanout) {
			if (f.isDirectory()) {
				String[] entries = f.list();
				if (entries == null || entries.length == 0) {
					assertFalse(
							"Found empty fanout directory "
									+ f.getAbsolutePath() + " after pruning"
							f.getName().length() == 2);
				}
			}
		}
	}
