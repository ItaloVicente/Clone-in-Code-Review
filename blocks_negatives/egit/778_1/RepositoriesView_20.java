					List<String> oldDirectories = new ArrayList<String>();
					for (RepositoryTreeNode<Repository> node : oldInput) {
						oldDirectories.add(node.getRepository().getDirectory()
								.getPath());
					}
					needsNewInput = !directories.containsAll(oldDirectories);
				}
