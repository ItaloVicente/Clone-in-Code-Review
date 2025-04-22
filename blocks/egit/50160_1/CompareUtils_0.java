			Repository repository,
			CompareEditorInput input) {
		if (GitPreferenceRoot.useExternalDiffTool()) {
			openInCompareExternal(repository, input);
		} else {
			openInCompareInternal(workBenchPage, input);
		}
	}

	private static void openInCompareInternal(IWorkbenchPage workBenchPage,
