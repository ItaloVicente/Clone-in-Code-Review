		private void applyTags(PackWriter pw
			for (Ref ref : allTags) {
				ObjectId objectId = ref.getObjectId();
				if (objectId == null) {
					continue;
				}

				if (wantAll.isEmpty()) {
					if (wantIds.contains(objectId))
						continue;
