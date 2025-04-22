		case REPOGROUP: {
			RepositoryGroups groups = new RepositoryGroups();
			List<RepositoryTreeNode<? extends Object>> nodeList = new ArrayList<>();

			for (String repoDir : groups.getRepositories(
					((RepositoryGroupNode) node).getObject())) {
				RepositoryNode rNode;
				try {
					rNode = new RepositoryNode(null, repositoryCache
							.lookupRepository(new File(repoDir)));
					nodeList.add(rNode);
				} catch (IOException e) {
				}
			}
			return nodeList.toArray();
		}

