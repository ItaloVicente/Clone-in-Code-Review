		RepositoryTreeNode node = (RepositoryTreeNode) element;
		if (node.getType() == RepositoryTreeNodeType.TAG) {
			try {
				ObjectId id = node.getRepository().resolve(
						((Ref) node.getObject()).getName());
				new RevWalk(node.getRepository()).parseTag(id);
				return decorateImage(node.getType().getIcon(), element);
			} catch (AmbiguousObjectException e) {
				return decorateImage(node.getType().getIcon(), element);
			} catch (IOException e) {
				return decorateImage(lightweightTagImage, element);
			}
		} else
			return decorateImage(node.getType().getIcon(), element);
