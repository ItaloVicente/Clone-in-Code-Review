	private void decorateNode(RepositoryTreeNode<?> node,
			@NonNull Repository repository, IDecoration decoration) {
		decorateText(node, repository, decoration);
		decorateIcon(node, repository, decoration);
	}

	private void decorateIcon(RepositoryTreeNode<?> node,
			@NonNull Repository repository, IDecoration decoration) {
		switch (node.getType()) {
		case TAG:
		case ADDITIONALREF:
		case REF:
			String refName = ((Ref) node.getObject()).getName();
			Ref leaf = ((Ref) node.getObject()).getLeaf();

			String compareString = null;
			String branchName = DecoratorRepositoryStateCache.INSTANCE
					.getFullBranchName(repository);
			if (branchName == null) {
				return;
			}
			if (refName.startsWith(Constants.R_HEADS)) {
				compareString = refName;
			} else if (refName.startsWith(Constants.R_TAGS)) {
				TagNode tagNode = (TagNode) node;
				compareString = tagNode.getCommitId();
			} else if (refName.startsWith(Constants.R_REMOTES)) {
				ObjectId objectId = leaf.getObjectId();
				if (objectId != null) {
					String leafName = objectId.getName();
					if (leafName.equals(branchName)) {
						decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
								IDecoration.TOP_LEFT);
						return;
					}
				}
			} else if (refName.equals(Constants.HEAD)) {
				decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
						IDecoration.TOP_LEFT);
				return;
			} else {
				String leafname = leaf.getName();
				if (leafname.startsWith(Constants.R_REFS)
						&& leafname.equals(branchName)) {
					decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
							IDecoration.TOP_LEFT);
					return;
				}
				ObjectId objectId = leaf.getObjectId();
				if (objectId != null && objectId
						.equals(DecoratorRepositoryStateCache.INSTANCE
								.getHead(repository))) {
					decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
							IDecoration.TOP_LEFT);
				}
				return;
			}

			if (compareString != null && compareString.equals(branchName)) {
				decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
						IDecoration.TOP_LEFT);
			}

			break;
		default:
			break;
		}
	}

