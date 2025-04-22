			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setToolTipText(WorkbenchMessages.MinimizePartAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.MINIMIZE_PART_ACTION);
			return action;
		}
	};
