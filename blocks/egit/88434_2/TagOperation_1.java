	public void execute(IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor,
				NLS.bind(CoreText.TagOperation_performingTagging, tag.getTag()),
				2);
		ObjectId tagId = updateTagObject();
		progress.worked(1);
		updateRepo(tagId);
		progress.worked(1);
