			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new org.eclipse.ui.actions.QuickStartAction(window);
			action.setId(getId());
			return action;
		}
	};

	@Deprecated
