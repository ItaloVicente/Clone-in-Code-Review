	protected void openInCompare(ExecutionEvent event, CompareEditorInput input)
			throws ExecutionException {
		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		CompareUtils.openInCompare(workBenchPage, input);
	}

