		if (selection != null) {
			for (IResource resource : SelectionUtils
					.getSelectedResources(selection)) {
				if (resource != null
						&& RepositoryMapping.getMapping(resource) != null) {
					return true;
