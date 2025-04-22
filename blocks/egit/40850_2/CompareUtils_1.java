	private static void openCompareEditorRunnable(
			final IWorkbenchPage page,
			final CompareEditorInput in) {
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					openCompareEditorRunnable(page, in);
				}
			});
			return;
		}
