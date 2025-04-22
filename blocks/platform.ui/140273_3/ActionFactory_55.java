			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new ToggleEditorsVisibilityAction(window);
			action.setId(getId());
			return action;
		}
	};
