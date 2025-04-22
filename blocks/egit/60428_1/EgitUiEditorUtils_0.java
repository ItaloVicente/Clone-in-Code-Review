		}
		IPath path = new Path(file.getAbsolutePath());
		IFile ifile = ResourceUtil.getFileForLocation(path, true);
		try {
			if (ifile != null) {
				return IDE.openEditor(page, ifile,
