	protected IFile getFileInput(ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchPart part = getPart(event);
		if (!(part instanceof IHistoryView))
			throw new ExecutionException(
					UIText.AbstractHistoryCommanndHandler_NoInputMessage);
		Object actInput = (((IHistoryView) part).getHistoryPage().getInput());
		if (actInput instanceof IFile)
			return (IFile) actInput;
		return null;
	}

	protected File getLocalFileInput(ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchPart part = getPart(event);
		if (!(part instanceof IHistoryView))
			throw new ExecutionException(
					UIText.AbstractHistoryCommanndHandler_NoInputMessage);
		Object actInput = (((IHistoryView) part).getHistoryPage().getInput());
		if (actInput instanceof FileNode)
			return ((FileNode) actInput).getObject();
		return null;
	}

	protected String getRepoRelativePath(Repository repo, File file){
		IPath workdirPath = new Path(repo.getWorkTree().getPath());
		IPath filePath = new Path(file.getPath()).setDevice(null);
		return filePath.removeFirstSegments(
				workdirPath.segmentCount()).toString();
	}

