				ObjectId id = null;
				if (ref != null) {
					id = ref.getLeaf().getObjectId();
				} else if (selection != null) {
					id = selection.getId();
				}
				initAndStartRevWalk(false, id);
