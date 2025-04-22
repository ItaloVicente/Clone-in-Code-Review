			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.CloseUnrelatedProjectsAction_text);
			action.setToolTipText(IDEWorkbenchMessages.CloseUnrelatedProjectsAction_toolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory NEW_WIZARD_DROP_DOWN = new ActionFactory(
			"newWizardDropDown") { //$NON-NLS-1$

		@Override
