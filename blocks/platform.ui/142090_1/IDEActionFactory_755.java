			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new BuildCleanAction(window);
			action.setId(getId());
			return action;
		}
	};

	public static final ActionFactory BUILD_AUTOMATICALLY = new ActionFactory("buildAutomatically") { //$NON-NLS-1$

		@Override
