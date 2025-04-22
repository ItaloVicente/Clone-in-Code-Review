		ITypedElement next = CompareUtils.getHeadTypedElement(repository, gitPath);
		if (next == null)
			return null;

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, next, null);

		IWorkbenchPage workBenchPage = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();
		CompareUtils.openInCompare(workBenchPage, in);

