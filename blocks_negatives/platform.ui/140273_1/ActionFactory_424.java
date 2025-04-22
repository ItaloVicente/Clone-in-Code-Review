            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
			action.setText(WorkbenchMessages.CyclePartAction_prev_text);
			action.setToolTipText(WorkbenchMessages.CyclePartAction_prev_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PART_BACKWARD_ACTION);
            return action;
        }
    };
