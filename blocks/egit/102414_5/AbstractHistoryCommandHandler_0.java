		IStructuredSelection selection = getSelection(event);
		if (!selection.isEmpty()) {
			IRepositoryCommit commit = AdapterUtils
					.adapt(selection.getFirstElement(), IRepositoryCommit.class);
			if (commit != null) {
				return commit.getRepository();
			}
		}
