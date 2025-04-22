			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new ToggleAutoBuildAction(window);
			action.setId(getId());
			action.setActionDefinitionId("org.eclipse.ui.project.buildAutomatically"); //$NON-NLS-1$
			return action;
		}
	};

	public static final ActionFactory BUILD_PROJECT = new ActionFactory(
			"buildProject", IWorkbenchCommandConstants.PROJECT_BUILD_PROJECT) { //$NON-NLS-1$

		@Override
