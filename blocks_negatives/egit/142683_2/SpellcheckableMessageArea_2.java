	private TextViewerAction createFromActionFactory(ActionFactory factory,
			int operationCode) {
		IWorkbenchAction template = factory
				.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		TextViewerAction action = new TextViewerAction(sourceViewer,
				operationCode);
		action.setText(template.getText());
		action.setImageDescriptor(template.getImageDescriptor());
		action.setDisabledImageDescriptor(
				template.getDisabledImageDescriptor());
		action.setActionDefinitionId(template.getActionDefinitionId());
		template.dispose();
		return action;
	}

