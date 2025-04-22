	private void showGpgProblem(IStatus status) {
		IAction action = new GpgConfigProblemReportAction(status,
				UIText.TagAction_gpgConfigProblem);
		PlatformUI.getWorkbench().getDisplay().asyncExec(action::run);
	}

