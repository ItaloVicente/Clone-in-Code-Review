			case REMOTES:

				List<RepositoryTreeNode<String>> remotes = new ArrayList<RepositoryTreeNode<String>>();

				Repository rep = node.getRepository();

				Set<String> configNames = rep.getConfig()
						.getSubsections(REMOTE);

				for (String configName : configNames) {
					remotes.add(new RepositoryTreeNode<String>(node,
							RepositoryTreeNodeType.REMOTE, repo, configName));
				}

				return remotes.toArray();

