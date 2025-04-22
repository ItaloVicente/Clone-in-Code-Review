	public void execute(IProgressMonitor m) throws CoreException {
		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;
		try {
			monitor.beginTask(NLS.bind(CoreText.TagOperation_performingTagging,
					tag.getTag()), 3);

			ObjectId tagId = updateTagObject();
			monitor.worked(1);

			updateRepo(tagId);
			monitor.worked(1);

		} finally {
			monitor.done();
		}
