			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_forward);
			action.setToolTipText(WorkbenchMessages.Workbench_forwardToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};
