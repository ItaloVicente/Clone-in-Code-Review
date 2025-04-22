			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_findReplace);
			action.setToolTipText(WorkbenchMessages.Workbench_findReplaceToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};
