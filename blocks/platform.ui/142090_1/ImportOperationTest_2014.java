		importElements.add(element);
		ImportOperation operation = new ImportOperation(project.getFullPath(),
				FileSystemStructureProvider.INSTANCE, this, importElements);

		operation.setCreateContainerStructure(false);
		openTestWindow().run(true, true, operation);

		try {
			IPath path = new Path(localDirectory);
			IResource targetFolder = project.findMember(path.lastSegment());

			assertTrue("Import failed", targetFolder instanceof IContainer);

			IResource[] resources = ((IContainer) targetFolder).members();
			assertEquals("Import failed to import all directories",
					directoryNames.length, resources.length);
			for (IResource resource : resources) {
				assertTrue("Import failed", resource instanceof IContainer);
				verifyFolder((IContainer) resource);
			}
		} catch (CoreException e) {
			fail(e.toString());
		}
	}

	public void testSetFilesToImport() throws Exception {
		project = FileUtil.createProject("ImportSetFilesToImport");
		File element = new File(localDirectory + File.separator
				+ directoryNames[0]);
		ImportOperation operation = new ImportOperation(project.getFullPath(),
				new File(localDirectory), FileSystemStructureProvider.INSTANCE,
				this);
