			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CloseEditorAction_text);
			action.setToolTipText(WorkbenchMessages.CloseEditorAction_toolTip);
			return action;
		}
	};
