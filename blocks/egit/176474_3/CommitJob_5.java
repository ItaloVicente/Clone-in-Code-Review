	private void showGpgProblem(IStatus status) {
		IAction action = new GpgConfigProblemReportAction(status,
				UIText.CommitJob_GpgConfigProblem);
		PlatformUI.getWorkbench().getDisplay().asyncExec(action::run);
	}

