			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(Constants.R_HEADS).entrySet()) {
					if (!refEntry.getValue().isSymbolic())
						refs.add(new RefNode(node, repo, refEntry.getValue()));
