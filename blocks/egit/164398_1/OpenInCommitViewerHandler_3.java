
	@Override
	public void updateElement(UIElement element, Map parameters) {
		IStructuredSelection selection = SelectionUtils.getSelection(
				PlatformUI.getWorkbench().getService(IEvaluationService.class)
						.getCurrentState());
		if (selection.size() > 1) {
			element.setText(UIText.GitHistoryPage_OpenAllInCommitViewerLabel);
		}
	}
