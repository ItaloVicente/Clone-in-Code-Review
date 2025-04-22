			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new TipsAndTricksAction(window);
			action.setId(getId());
			return action;
		}
	};
