
		if (file != null)
			next = SaveableCompareEditorInput.createFileElement(file);
		else
			next = new LocalNonWorkspaceTypedElement(new Path(getRepository()
					.getWorkTree().getAbsolutePath()).append(p));

		if (d.getChange().equals(ChangeType.DELETE))
		else
			base = CompareUtils.getFileRevisionTypedElement(p, commit,
					getRepository(), blobs[blobs.length - 1]);

		in = new GitCompareFileRevisionEditorInput(next, base, null);
		CompareUtils.openInCompare(site.getWorkbenchWindow().getActivePage(),
				in);
