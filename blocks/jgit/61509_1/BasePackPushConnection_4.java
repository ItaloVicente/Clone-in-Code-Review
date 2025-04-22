			ObjectId oldId = null;
			if (advertisedRef != null) {
				ObjectId objectId = advertisedRef.getObjectId();
				if (objectId != null) {
					oldId = objectId;
				}
			}
			if (oldId == null) {
				oldId = ObjectId.zeroId();
			}
