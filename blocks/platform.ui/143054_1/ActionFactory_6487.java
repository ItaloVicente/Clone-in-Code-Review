			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new DynamicHelpAction(window);
			action.setId(getId());
			return action;
		}
	};
