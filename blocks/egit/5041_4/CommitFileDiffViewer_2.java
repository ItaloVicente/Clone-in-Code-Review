	void showWorkingDirectoryFileDiff(final FileDiff d) {
		final GitCompareFileRevisionEditorInput in;

		final String p = d.getPath();
		final RevCommit c = d.getCommit();
		final ObjectId[] blobs = d.getBlobs();
		final ITypedElement base;
		final ITypedElement next;

		String path = new Path(getRepository().getWorkTree().getAbsolutePath())
				.append(p).toOSString();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = root.findFilesForLocationURI(new File(path).toURI());
		if (files.length > 0)
			next = SaveableCompareEditorInput.createFileElement(files[0]);
		else
			next = new LocalNonWorkspaceTypedElement(path);

		if (d.getChange().equals(ChangeType.DELETE))
			base = new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$
		else
			base = CompareUtils.getFileRevisionTypedElement(p, c,
					getRepository(), blobs[blobs.length - 1]);

		in = new GitCompareFileRevisionEditorInput(next, base, null);
		CompareUtils.openInCompare(site.getWorkbenchWindow().getActivePage(),
				in);
	}

