		String path = new Path(getRepository().getWorkTree().getAbsolutePath())
				.append(p).toOSString();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = root.findFilesForLocationURI(new File(path).toURI());
		if (files.length > 0)
			next = SaveableCompareEditorInput.createFileElement(files[0]);
