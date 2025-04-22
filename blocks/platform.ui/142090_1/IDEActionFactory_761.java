			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.OpenResourceAction_text);
			action.setToolTipText(IDEWorkbenchMessages.OpenResourceAction_toolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory OPEN_WORKSPACE = new ActionFactory(
			"openWorkspace") { //$NON-NLS-1$

		@Override
