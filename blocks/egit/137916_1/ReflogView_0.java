				if (element instanceof RefNode) {
					RefNode node = (RefNode) element;
					Ref ref = node.getObject();
					showReflogFor(node.getRepository(),
							Repository.shortenRefName(ref.getName()));
					return true;
				} else if (element instanceof RepositoryTreeNode) {
