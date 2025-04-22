	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		progress.setTaskName(NLS.bind(CoreText.TagOperation_performingTagging,
				tag.getTag()));
		ObjectId tagId = annotated ? updateTagObject() : tag.getObjectId();
		progress.worked(1);
		updateRepo(tagId);
		progress.worked(1);
