	public static void openInCompareEditor(IWorkbenchPage workBenchPage,
			CompareEditorInput input) {
		if (GitPreferenceRoot.useExternalDiffTool()) {
			CompareUtils.openCompareToolExternal(input);
		} else {
			CompareUtils.openInCompareEditorInternal(workBenchPage, input);
		}
	}

	public static void openInCompareDialog(CompareEditorInput input) {
		if (GitPreferenceRoot.useExternalDiffTool()) {
			CompareUtils.openCompareToolExternal(input);
		} else {
			CompareUtils.openInCompareDialogInternal(input);
		}
	}

	private static void openInCompareDialogInternal(CompareEditorInput input) {
		CompareUI.openCompareDialog(input);
	}

	private static void openInCompareEditorInternal(IWorkbenchPage workBenchPage,
