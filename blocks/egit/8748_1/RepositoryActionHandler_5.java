			if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
				if (warn)
					MessageDialog.openError(shell,
							UIText.RepositoryAction_linkedResourceSelectionTitle,
							UIText.RepositoryAction_linkedResourceSelection);
				return null;
			}
