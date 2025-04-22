
			List<RepositoryTreeNode<String>> nodes = new ArrayList<RepositoryTreeNode<String>>();

			nodes.add(new RepositoryTreeNode<String>(node,
					RepositoryTreeNodeType.LOCALBRANCHES, repo, "")); //$NON-NLS-1$
			nodes.add(new RepositoryTreeNode<String>(node,
					RepositoryTreeNodeType.REMOTEBRANCHES, repo, "")); //$NON-NLS-1$

			return nodes.toArray();
		}

		case LOCALBRANCHES: {
			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(Constants.R_HEADS).entrySet()) {
					if (!refEntry.getValue().isSymbolic())
						refs.add(new RepositoryTreeNode<Ref>(node,
								RepositoryTreeNodeType.REF, repo, refEntry
										.getValue()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return refs.toArray();
		}

		case REMOTEBRANCHES: {
