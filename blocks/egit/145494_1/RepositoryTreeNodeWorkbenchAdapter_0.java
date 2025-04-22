				ObjectId objectId = leaf.getObjectId();
				if (objectId != null) {
					String leafName = objectId.getName();
					if (leafName.equals(branchName)) {
						return new DecorationOverlayDescriptor(base,
								UIIcons.OVR_CHECKEDOUT, IDecoration.TOP_LEFT);
					}
