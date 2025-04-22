			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new CloseAllSavedAction(window);
			action.setId(getId());
			return action;
		}
	};
