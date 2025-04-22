			nodeList.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.BRANCHES, node.getRepository(), node
							.getRepository()));

			nodeList.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.TAGS, repo, repo));

			nodeList.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.SYMBOLICREFS, repo, repo));

			nodeList.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.WORKINGDIR, node.getRepository(),
					node.getRepository()));

			nodeList.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.REMOTES, node.getRepository(), node
							.getRepository()));
