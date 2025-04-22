				if (resource.getRepositoryName() != null
						&& resource.getBranch() != null) {
					format = store.getString(
							UIPreferences.DECORATOR_PROJECTTEXT_DECORATION);
				} else {
					format = store.getString(
							UIPreferences.DECORATOR_FOLDERTEXT_DECORATION);
				}
