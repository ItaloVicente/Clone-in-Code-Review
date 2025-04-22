	@Test
	public void hasLocalChangeWhenRefreshingParentFolder() throws Exception {
		IFolder folder = iProject.getFolder("newfolder");
		folder.create(false, true, null);

		IFile file = folder.getFile("a.txt");
		file.create(new ByteArrayInputStream("a".getBytes()), false, null);

		RemoteResourceMappingContext context = prepareContext(MASTER, MASTER);
		refresh(context, file);

		assertTrue(context.hasLocalChange(file, new NullProgressMonitor()));

		file.delete(false, null);

		refresh(context, folder);

		assertFalse(context.hasLocalChange(file, new NullProgressMonitor()));
	}

