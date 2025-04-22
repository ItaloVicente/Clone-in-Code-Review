			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.CloseResourceAction_text);
			action.setToolTipText(IDEWorkbenchMessages.CloseResourceAction_text);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory CLOSE_UNRELATED_PROJECTS = new ActionFactory(
			"closeUnrelatedProjects", IWorkbenchCommandConstants.PROJECT_CLOSE_UNRELATED_PROJECTS) { //$NON-NLS-1$

		@Override
