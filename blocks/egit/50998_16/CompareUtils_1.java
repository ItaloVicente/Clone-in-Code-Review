			Repository repository,
			CompareEditorInput input) {
		if (GitPreferenceRoot.useExternalDiffTool()) {
			openCompareToolExternal(repository, input);
		} else {
			openCompareToolInternal(workbenchPage, input);
		}
	}

	private static void openCompareToolInternal(IWorkbenchPage workbenchPage,
