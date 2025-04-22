		case REPOGROUP: {
			List<RepositoryTreeNode<? extends Object>> nodeList = new ArrayList<>();

			for (String repoDir : ((RepositoryGroupNode) node).getGroup()
					.getRepositories()) {
				RepositoryNode rNode;
				try {
					rNode = new RepositoryNode(node, repositoryCache
							.lookupRepository(new File(repoDir)));
					nodeList.add(rNode);
				} catch (IOException e) {
				}
			}
			return nodeList.toArray();
		}

