		return numberOfNodes == 1 && getRef(nodes.get(0)) != null;
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		IStructuredSelection selection = SelectionUtils.getSelection(
				PlatformUI.getWorkbench().getService(IEvaluationService.class)
						.getCurrentState());
		if (selection.size() == 1) {
			element.setText(UIText.CompareCommand_WithWorkingTreeLabel);
		}
