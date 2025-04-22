		} else if (type == RepositoryTreeNodeType.REPO) {
			Object object = node.getObject();
			if (object instanceof Repository) {
				Repository r = (Repository) object;
				if (ResourcePropertyTester.testRepositoryState(r,
						"hasGerritConfiguration")) //$NON-NLS-1$
					return gerritRepoImage;
			}
