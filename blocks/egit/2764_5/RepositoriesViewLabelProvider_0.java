		RepositoryTreeNode node = (RepositoryTreeNode) element;
		if (node.getType() == RepositoryTreeNodeType.TAG) {
			RevObject any;
			try {
				ObjectId id = node.getRepository().resolve(
						((Ref) node.getObject()).getName());
				any = new RevWalk(node.getRepository()).parseAny(id);
			} catch (MissingObjectException e) {
				Activator.logError(e.getMessage(), e);
				return null;
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
				return null;
			}
			if (any instanceof RevCommit)
				return decorateImage(lightweightTagImage, element);
			else
				return decorateImage(node.getType().getIcon(), element);
		} else
			return decorateImage(node.getType().getIcon(), element);
