			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(RefDatabase.ALL).entrySet()) {
					if (refEntry.getValue().isSymbolic())
						refs.add(new RepositoryTreeNode<Ref>(node,
								RepositoryTreeNodeType.SYMBOLICREF, repo,
								refEntry.getValue()));
				}
			} catch (IOException e) {
				handleException(e, node);
