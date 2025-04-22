			action.setId(getId());
			action.setText(WorkbenchMessages.ClosePerspectiveAction_text);
			action.setToolTipText(WorkbenchMessages.ClosePerspectiveAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.CLOSE_PAGE_ACTION);
			return action;
		}
	};
