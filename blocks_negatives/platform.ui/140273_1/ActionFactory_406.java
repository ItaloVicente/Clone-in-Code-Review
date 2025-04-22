            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CloseAllAction_text);
            action.setToolTipText(WorkbenchMessages.CloseAllAction_toolTip);
            return action;
        }
    };
