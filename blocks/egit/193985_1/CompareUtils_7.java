			Repository repository, CompareEditorInput input) {
		if (DiffMergeSettings.useInternalDiffTool()) {
			openCompareToolInternal(workbenchPage, input);
		} else {
			openCompareToolExternal(workbenchPage, repository, input);
		}
	}

	private static void openCompareToolInternal(IWorkbenchPage workbenchPage,
