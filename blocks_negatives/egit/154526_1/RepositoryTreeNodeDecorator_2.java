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
