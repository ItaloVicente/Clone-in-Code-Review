	public ShowInContext getShowInContext() {
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		List<IFile> files = new ArrayList<IFile>();
		for (Object element : selection.toList()) {
			FileDiff fileDiff = (FileDiff) element;
			IFile file = ResourceUtil.getFileForLocation(db, fileDiff.getPath());
			if (file != null)
				files.add(file);
		}
		return new ShowInContext(null, new StructuredSelection(files));
	}

