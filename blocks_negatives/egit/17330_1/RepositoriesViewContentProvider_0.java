			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			try {
				for (Entry<String, Ref> refEntry : getRefs(repo, Constants.R_TAGS).entrySet()) {
					refs.add(new TagNode(node, repo, refEntry.getValue()));
				}
			} catch (IOException e) {
				return handleException(e, node);
			}

			return refs.toArray();
