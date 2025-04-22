			Repository repository,
			CompareEditorInput input) {
		if (GitPreferenceRoot.useExternalDiffTool()) {
			openCompareToolExternal(repository, input);
		} else {
			openCompareToolInternal(workBenchPage, input);
		}
	}

	private static void openCompareToolInternal(IWorkbenchPage workBenchPage,
