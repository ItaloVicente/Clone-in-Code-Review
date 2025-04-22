			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(Constants.R_REMOTES).entrySet()) {
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
				e.printStackTrace();
			}

			return refs.toArray();
		}

		case SYMBOLICREFS: {
			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			try {
				for (Entry<String, Ref> refEntry : repo.getRefDatabase()
						.getRefs(RefDatabase.ALL).entrySet()) {
					if (refEntry.getValue().isSymbolic())
						refs.add(new RepositoryTreeNode<Ref>(node,
								RepositoryTreeNodeType.SYMBOLICREF, repo,
								refEntry.getValue()));
				}
			} catch (IOException e) {
				e.printStackTrace();
