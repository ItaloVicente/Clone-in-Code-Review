
			List<RepositoryTreeNode<Repository>> nodes = new ArrayList<RepositoryTreeNode<Repository>>();

			nodes.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.LOCALBRANCHES, repo, repo));
			nodes.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.REMOTEBRANCHES, repo, repo));

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
				handleException(e, node);
			}

			return refs.toArray();
		}

		case REMOTEBRANCHES: {
			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(Constants.R_REMOTES).entrySet()) {
					if (!refEntry.getValue().isSymbolic())
						refs.add(new RepositoryTreeNode<Ref>(node,
								RepositoryTreeNodeType.REF, repo, refEntry
										.getValue()));
				}
			} catch (IOException e) {
				handleException(e, node);
			}

			return refs.toArray();
		}
		case TAGS: {
			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(Constants.R_TAGS).entrySet()) {
					refs.add(new RepositoryTreeNode<Ref>(node,
							RepositoryTreeNodeType.TAG, repo, refEntry
									.getValue()));
				}
			} catch (IOException e) {
				handleException(e, node);
			}

			return refs.toArray();
		}

		case SYMBOLICREFS: {
