				}
			}

		} catch (CoreException e) {
			fail(e.toString());
		}
	}

	private void verifyFolder(IContainer folder) {
		try {
			IResource[] files = folder.members();
			assertEquals("Import failed to import all files", fileNames.length,
					files.length);
			for (String fileName : fileNames) {
				int k;
				for (k = 0; k < files.length; k++) {
					if (fileName.equals(files[k].getName())) {
