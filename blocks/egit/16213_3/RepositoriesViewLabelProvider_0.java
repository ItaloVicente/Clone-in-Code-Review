		} else if (type == RepositoryTreeNodeType.REPO) {
			Object object = node.getObject();
			if (object instanceof Repository) {
				Repository r = (Repository) object;
				if (ResourcePropertyTester.hasGerritConfiguration(r))
					return gerritRepoImage;
			}
