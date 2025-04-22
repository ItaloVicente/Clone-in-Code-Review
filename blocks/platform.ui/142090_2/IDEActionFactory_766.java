			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.Workbench_rebuildProject);
			action.setToolTipText(IDEWorkbenchMessages.Workbench_rebuildProjectToolTip);
			window.getPartService().addPartListener(action);
			action
					.setActionDefinitionId("org.eclipse.ui.project.rebuildProject"); //$NON-NLS-1$
			return action;
		}
	};

	public static final ActionFactory TIPS_AND_TRICKS = new ActionFactory(
			"tipsAndTricks", IWorkbenchCommandConstants.HELP_TIPS_AND_TRICKS) { //$NON-NLS-1$

		@Override
