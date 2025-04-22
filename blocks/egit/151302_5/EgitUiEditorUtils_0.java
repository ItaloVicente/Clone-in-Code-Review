	public static IEditorPart findEditor(File file, IWorkbenchPage page) {
		if (!file.exists() || page == null) {
			return null;
		}
		IPath path = new Path(file.getAbsolutePath());
		IFile iFile = ResourceUtil.getFileForLocation(path, true);
		if (iFile != null) {
			return page.findEditor(new FileEditorInput(iFile));
		} else {
			IFileStore store = EFS.getLocalFileSystem().getStore(path);
			return page.findEditor(new FileStoreEditorInput(store));
		}
	}

