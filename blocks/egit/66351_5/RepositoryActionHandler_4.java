		if (selection != null) {
			IResource[] resources = SelectionUtils
					.getSelectedResources(selection);
			if (resources.length > 0) {
				for (IResource resource : resources) {
					if (resource == null
							|| RepositoryMapping.getMapping(resource) == null) {
						return false;
					}
