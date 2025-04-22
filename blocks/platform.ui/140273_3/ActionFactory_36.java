			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.MINIMIZE_PART_ACTION);
			return action;
		}
	};

	public static final ActionFactory MOVE = new ActionFactory("move", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_MOVE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_move);
			action.setToolTipText(WorkbenchMessages.Workbench_moveToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory NEW = new ActionFactory("new", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_NEW) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			ISharedImages images = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
			action.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
			action.setText(WorkbenchMessages.NewWizardAction_text);
			action.setToolTipText(WorkbenchMessages.NewWizardAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.NEW_ACTION);
			return action;
		}
	};

