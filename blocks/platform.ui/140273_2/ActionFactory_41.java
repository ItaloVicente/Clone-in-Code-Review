			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_previous);
			action.setToolTipText(WorkbenchMessages.Workbench_previousToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};
