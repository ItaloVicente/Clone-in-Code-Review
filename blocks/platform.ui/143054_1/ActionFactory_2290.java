			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_back);
			action.setToolTipText(WorkbenchMessages.Workbench_backToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};
