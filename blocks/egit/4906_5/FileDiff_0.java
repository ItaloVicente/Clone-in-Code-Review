		final boolean submodule = diffEntry.getNewMode() == FileMode.GITLINK
				|| diffEntry.getOldMode() == FileMode.GITLINK;
		final ImageDescriptor base;
		if (!submodule)
			base = UIUtils.getEditorImage(getPath());
		else
			base = UIIcons.REPOSITORY;
