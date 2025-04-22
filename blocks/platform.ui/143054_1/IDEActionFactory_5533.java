			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new ProjectPropertyDialogAction(window);
			action.setId(getId());
			return action;
		}
	};

	@Deprecated
