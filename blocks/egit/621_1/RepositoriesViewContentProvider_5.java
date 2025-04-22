			List<RepositoryTreeNode<? extends Object>> nodeList = new ArrayList<RepositoryTreeNode<? extends Object>>();

			try {
				Ref headRef = repo.getRef(Constants.HEAD);
				if (headRef != null)

					nodeList.add(new RepositoryTreeNode<Ref>(node,
							RepositoryTreeNodeType.HEAD, node.getRepository(),
							headRef));
			} catch (IOException e) {
				e.printStackTrace();
			}

			nodeList.add(new RepositoryTreeNode<Repository>(node,
