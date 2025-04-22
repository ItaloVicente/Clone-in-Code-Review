
	private void showNoHeadWarning(final ExecutionEvent event) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openWarning(HandlerUtil.getActiveShell(event),
						UIText.PushBranchActionHandler_EmptyRepository,
						UIText.PushBranchActionHandler_NoHead);
			}
		});
	}
