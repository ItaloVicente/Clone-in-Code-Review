		ResourcesPlugin.getWorkspace().run(action, getSchedulingRule(),
				IWorkspace.AVOID_UPDATE, m);
	}

	private void reword(IProgressMonitor monitor)
			throws IOException, CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, MessageFormat.format(
				CoreText.RewordCommitOperation_rewording,
				Utils.getShortObjectId(commit)), 100);
