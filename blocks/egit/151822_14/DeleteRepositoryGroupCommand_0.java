			IPreferenceStore store = Activator.getDefault()
					.getPreferenceStore();

			boolean delete = true;
			if (numberOfRepos > 0 && store
					.getBoolean(UIPreferences.SHOW_DELETE_REPO_GROUP_WARNING)) {
				DeleteRepositoryGroupConfirmDialog confirmDelete = new DeleteRepositoryGroupConfirmDialog(
						getShell(event), groupsNodes);
				delete = confirmDelete.open() == Window.OK;
				if (!confirmDelete.showAgain()) {
					store.setValue(UIPreferences.SHOW_DELETE_REPO_GROUP_WARNING,
							false);
				}
			}
			if (delete) {
