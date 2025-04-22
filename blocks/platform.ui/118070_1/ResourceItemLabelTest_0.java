	public void testBug531610() throws Exception {
		IFolder folder = project.getFolder("folder");
		IFile fileB = folder.getFile("file");
		folder.create(true, true, new NullProgressMonitor());
		fileB.create(stream, true, new NullProgressMonitor());
		System.out.println(project.getName());
		Position[] withDigits = { new Position(0, 4),
				new Position(4, 3 + project.getName().length()) }; // " - ".length() == 3
		compareStyleRanges(withDigits, getStyleRanges("file", "file"));
	}

