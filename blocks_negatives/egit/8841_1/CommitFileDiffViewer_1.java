		IPath path = new Path(getRepository().getWorkTree().getAbsolutePath())
				.append(p);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = root.findFilesForLocationURI(path.toFile().toURI());
		if (files.length > 0)
			next = SaveableCompareEditorInput.createFileElement(files[0]);
