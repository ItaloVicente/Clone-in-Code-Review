			if (req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)
					&& allTags != null) {
				for (Ref ref : allTags) {
					ObjectId objectId = ref.getObjectId();
					if (objectId == null) {
						continue;
					}
