		return new ObjectIdSet() {
			@Override
			public boolean contains(AnyObjectId objectId) {
				for (ObjectId id : ids) {
					if (id.equals(objectId)) {
						return true;
					}
