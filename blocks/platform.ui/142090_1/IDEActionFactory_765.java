			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new GlobalBuildAction(window,
					IncrementalProjectBuilder.FULL_BUILD);
			action.setId(getId());
			return action;
		}
	};

	@Deprecated
