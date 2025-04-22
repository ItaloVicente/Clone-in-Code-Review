		protected void applyTags(PackWriter pw
			for (Ref ref : allTags) {
				ObjectId objectId = ref.getObjectId();
				if (objectId == null) {
					continue;
				}
