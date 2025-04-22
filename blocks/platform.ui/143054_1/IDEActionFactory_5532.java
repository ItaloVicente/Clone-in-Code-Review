			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new OpenWorkspaceAction(window);
			action.setId(getId());
			return action;
		}
	};

	public static final ActionFactory OPEN_PROJECT_PROPERTIES = new ActionFactory(
			"projectProperties") { //$NON-NLS-1$

		@Override
