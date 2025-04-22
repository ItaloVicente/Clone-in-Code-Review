			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new NavigationHistoryAction(window, false);
			action.setId(getId());
			return action;
		}
	};
