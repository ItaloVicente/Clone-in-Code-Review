	private ImageDescriptor decorateImageDescriptor(
			@NonNull ImageDescriptor base, @NonNull RepositoryTreeNode<?> node)
			throws IOException {
		switch (node.getType()) {
		case TAG:
		case ADDITIONALREF:
		case REF:
			String refName = ((Ref) node.getObject()).getName();
			Ref leaf = ((Ref) node.getObject()).getLeaf();

			String compareString = null;
			Repository repository = node.getRepository();
			String branchName = repository.getFullBranch();
			if (branchName == null) {
				return base;
			}
			if (refName.startsWith(Constants.R_HEADS)) {
				compareString = refName;
			} else if (refName.startsWith(Constants.R_TAGS)) {
				TagNode tagNode = (TagNode) node;
				compareString = tagNode.getCommitId();
			} else if (refName.startsWith(Constants.R_REMOTES)) {
				ObjectId id = repository.resolve(refName);
				if (id == null) {
					return base;
				}
				try (RevWalk rw = new RevWalk(repository)) {
					RevCommit commit = rw.parseCommit(id);
					compareString = commit.getId().name();
				}
			} else if (refName.equals(Constants.HEAD)) {
				return new DecorationOverlayDescriptor(base,
						UIIcons.OVR_CHECKEDOUT, IDecoration.TOP_LEFT);
			} else {
				String leafname = leaf.getName();
				if (leafname.startsWith(Constants.R_REFS)
						&& leafname.equals(branchName)) {
					return new DecorationOverlayDescriptor(base,
							UIIcons.OVR_CHECKEDOUT, IDecoration.TOP_LEFT);
				}
				ObjectId objectId = leaf.getObjectId();
				if (objectId != null && objectId
						.equals(repository.resolve(Constants.HEAD))) {
					return new DecorationOverlayDescriptor(base,
							UIIcons.OVR_CHECKEDOUT, IDecoration.TOP_LEFT);
				}
				return base;
			}

			if (compareString != null && compareString.equals(branchName)) {
				return new DecorationOverlayDescriptor(base,
						UIIcons.OVR_CHECKEDOUT, IDecoration.TOP_LEFT);
			}

			break;
		default:
			break;
		}
		return base;
	}

