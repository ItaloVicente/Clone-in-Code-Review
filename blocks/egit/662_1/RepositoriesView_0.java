		boolean repoOnly = true;
		for (Object selected : sel.toArray()) {

			if (((RepositoryTreeNode) selected).getType() != RepositoryTreeNodeType.REPO) {
				repoOnly = false;
				break;
			}
		}

		if (sel.size() > 1 && repoOnly) {
			List nodes = sel.toList();
			final Repository[] repos = new Repository[nodes.size()];
			for (int i = 0; i < sel.size(); i++)
				repos[i] = ((RepositoryTreeNode) nodes.get(i)).getRepository();

			MenuItem remove = new MenuItem(men, SWT.PUSH);
			remove.setText(UIText.RepositoriesView_Remove_MenuItem);
			remove.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					removeRepository(new NullProgressMonitor(), repos);
				}
			});

		}

