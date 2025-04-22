			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new HelpSearchAction(window);
			action.setId(getId());
			return action;
		}
	};
