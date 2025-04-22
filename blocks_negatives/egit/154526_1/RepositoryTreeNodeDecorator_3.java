				ObjectId objectId = leaf.getObjectId();
				if (objectId != null && objectId
						.equals(DecoratorRepositoryStateCache.INSTANCE
								.getHead(repository))) {
					decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
							IDecoration.TOP_LEFT);
				}
