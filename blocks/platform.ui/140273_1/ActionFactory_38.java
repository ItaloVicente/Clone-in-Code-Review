			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.OpenInNewWindowAction_text);
			action.setToolTipText(WorkbenchMessages.OpenInNewWindowAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.OPEN_NEW_WINDOW_ACTION);
			return action;
		}
