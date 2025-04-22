		for (IResource resource : getSelectedResources(selection)) {
			if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
				if (warn)
					MessageDialog.openError(shell,
							UIText.RepositoryAction_linkedResourceSelectionTitle,
							UIText.RepositoryAction_linkedResourceSelection);
				return null;
			}
			IPath location = resource.getLocation();
			if (location == null)
				return null;
