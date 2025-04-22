			IPath path = object.getLocation();
			if (path != null) {
				File file = path.toFile();
				if (file != null) {
					IResourceState state = ResourceStateFactory.getInstance()
							.get(file);
					if (!state.isTracked() || state.isDirty()
							|| state.isMissing()) {
						menu.appendToGroup(GIT_ACTIONS,
								createItem(ADD_TO_INDEX));
					}
					if (state.isStaged()) {
						menu.appendToGroup(GIT_ACTIONS,
								createItem(REMOVE_FROM_INDEX));
					}
					if (!state.isIgnored()) {
						menu.appendToGroup(GIT_ACTIONS,
								createItem(IGNORE_ACTION));
					}
				}
			}
