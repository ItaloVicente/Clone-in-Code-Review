		IFile zarFile = project.getProject().getFile("zar/b.txt");
		IPath zarFilePath = zarFile.getLocation();
		assertTrue("could not delete file " + zarFilePath.toOSString(),
				zarFilePath.toFile().delete());
		zarFile.refreshLocal(0, null);

