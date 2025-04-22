			ObjectId advertisedOld = null;
			if (advertisedRef != null) {
				advertisedOld = advertisedRef.getObjectId();
			}
			if (advertisedOld == null) {
				advertisedOld = ObjectId.zeroId();
			}
