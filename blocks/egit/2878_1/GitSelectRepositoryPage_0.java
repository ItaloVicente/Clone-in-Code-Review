	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		if (visible && tv.getSelection().isEmpty()) {
			String repoDir = settings.get(LAST_SELECTED_REPO_PREF);
			if (repoDir != null) {
				for (TableItem item : tv.getTable().getItems()) {
					RepositoryNode node = (RepositoryNode) item.getData();
					if (node.getRepository().getDirectory().getPath().equals(
							repoDir)) {
						tv.setSelection(new StructuredSelection(node));
					}
				}
			}
		} else {
			Object element = ((IStructuredSelection) tv.getSelection())
					.getFirstElement();
			if (element instanceof RepositoryNode)
				settings.put(LAST_SELECTED_REPO_PREF,
						((RepositoryNode) element).getRepository()
								.getDirectory().getPath());
		}
	}

