	}

	private void decorateImage(RepositoryTreeNode<?> node,
			@NonNull Repository repository, IDecoration decoration)
			throws IOException {

		switch (node.getType()) {
		case TAG:
		case ADDITIONALREF:
		case REF:
			String refName = ((Ref) node.getObject()).getName();
			Ref leaf = ((Ref) node.getObject()).getLeaf();

			String compareString = null;

			String branchName = repository.getFullBranch();
			if (branchName == null) {
				return;
			}
			if (refName.startsWith(Constants.R_HEADS)) {
				compareString = refName;
			} else if (refName.startsWith(Constants.R_TAGS)) {
				TagNode tagNode = (TagNode) node;
				compareString = tagNode.getCommitId();
			} else if (refName.startsWith(Constants.R_REMOTES)) {
				ObjectId id = repository.resolve(refName);
				if (id == null) {
					return;
				}
				try (RevWalk rw = new RevWalk(repository)) {
					RevCommit commit = rw.parseCommit(id);
					compareString = commit.getId().name();
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
						.equals(repository.resolve(Constants.HEAD))) {
					decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
							IDecoration.TOP_LEFT);
					return;
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
