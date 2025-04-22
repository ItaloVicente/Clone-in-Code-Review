		Comparator<RepositoryTreeNode<Repository>> sorter = new Comparator<RepositoryTreeNode<Repository>>() {

			public int compare(RepositoryTreeNode<Repository> o1,
					RepositoryTreeNode<Repository> o2) {
				return getRepositoryName(o1.getObject()).compareTo(
						getRepositoryName(o2.getObject()));
			}

		};

		Set<RepositoryTreeNode<Repository>> output = new TreeSet<RepositoryTreeNode<Repository>>(
				sorter);

		for (Repository repo : ((List<Repository>) inputElement)) {
			output.add(new RepositoryTreeNode<Repository>(null,
					RepositoryTreeNodeType.REPO, repo, repo));
		}

		return output.toArray();
