		if (GitPreferenceRoot.useExternalDiffTool()) {
			openCompareToolExternal(input);
		} else {
			openCompareToolInternal(workBenchPage, input);
		}
	}

	private static void openCompareToolInternal(IWorkbenchPage workBenchPage,
			CompareEditorInput input) {
