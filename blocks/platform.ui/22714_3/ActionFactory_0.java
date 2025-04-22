		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setToolTipText(WorkbenchMessages.LockToolBarAction_toolTip);
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.LOCK_TOOLBAR_ACTION);
			return action;
		}
