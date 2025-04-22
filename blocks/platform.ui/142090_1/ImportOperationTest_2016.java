		importElements.add(element);
		ImportOperation operation = new ImportOperation(project.getFullPath(),
				FileSystemStructureProvider.INSTANCE, this, importElements);

		openTestWindow().run(true, true, operation);
		operation.setOverwriteResources(true);
		openTestWindow().run(true, true, operation);
	}

	private void verifyFiles(int folderCount) {
		try {
			IPath path = new Path(localDirectory);
			IResource targetFolder = project.findMember(path.makeRelative());

			assertTrue("Import failed", targetFolder instanceof IContainer);

			IResource[] resources = ((IContainer) targetFolder).members();
			assertEquals("Import failed to import all directories",
					folderCount, resources.length);
			for (IResource resource : resources) {
				assertTrue("Import failed", resource instanceof IContainer);
				verifyFolder((IContainer) resource);
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
