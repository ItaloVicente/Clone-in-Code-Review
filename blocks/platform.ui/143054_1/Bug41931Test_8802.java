		InputStream contents = new ByteArrayInputStream(new byte[0]);
		IFile fileA = testProject.getFile("a.txt"); //$NON-NLS-1$
		fileA.create(contents, true, null);
		IFile fileB = testProject.getFile("b.txt"); //$NON-NLS-1$
		fileB.create(contents, true, null);
		IFile fileC = testProject.getFile("c.txt"); //$NON-NLS-1$
		fileC.create(contents, true, null);
