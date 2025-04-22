				@Override
				public void widgetSelected(SelectionEvent e) {
					removeRepository(new NullProgressMonitor(), repos);
				}
			});

		}

		if (sel.size() > 1)
			return;

		final RepositoryTreeNode node = (RepositoryTreeNode) sel
				.getFirstElement();

		final boolean isBare = isBare(node.getRepository());
