			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.ExportResourcesAction_fileMenuText);
			action.setToolTipText(WorkbenchMessages.ExportResourcesAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.EXPORT_ACTION);
			action.setImageDescriptor(
					WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));
			return action;
		}

	};

	public static final ActionFactory FIND = new ActionFactory("find", //$NON-NLS-1$
			IWorkbenchCommandConstants.EDIT_FIND_AND_REPLACE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
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

	public static final ActionFactory FORWARD = new ActionFactory("forward", //$NON-NLS-1$
			IWorkbenchCommandConstants.NAVIGATE_FORWARD) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_forward);
			action.setToolTipText(WorkbenchMessages.Workbench_forwardToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory FORWARD_HISTORY = new ActionFactory("forwardHistory", //$NON-NLS-1$
			IWorkbenchCommandConstants.NAVIGATE_FORWARD_HISTORY) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new NavigationHistoryAction(window, true);
			action.setId(getId());
			return action;
		}
	};

	public static final ActionFactory GO_INTO = new ActionFactory("goInto", //$NON-NLS-1$
			IWorkbenchCommandConstants.NAVIGATE_GO_INTO) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_goInto);
			action.setToolTipText(WorkbenchMessages.Workbench_goIntoToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory IMPORT = new ActionFactory("import", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_IMPORT) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}

			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.ImportResourcesAction_text);
			action.setToolTipText(WorkbenchMessages.ImportResourcesAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.IMPORT_ACTION);
			action.setImageDescriptor(
					WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ));
			return action;

		}
	};

