		if (part == RepositoriesView.this) {
			if (!selection.isEmpty()
					&& selection instanceof IStructuredSelection) {
				Repository repo = SelectionUtils
						.getRepository((IStructuredSelection) selection);
				if (repo != null) {
					lastSelectedRepository = repo.getDirectory();
				} else {
					lastSelectedRepository = null;
				}
			}
