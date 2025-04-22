			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.Workbench_addTask);
			action.setToolTipText(IDEWorkbenchMessages.Workbench_addTaskToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory BOOKMARK = new ActionFactory("bookmark", //$NON-NLS-1$
			IWorkbenchCommandConstants.EDIT_ADD_BOOKMARK) {

		@Override
