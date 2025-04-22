		final ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> packedObjs = pw
				.getObjectSet();
		newPackObj.add(new PackWriter.ObjectIdSet() {
			public boolean contains(AnyObjectId objectId) {
				return packedObjs.contains(objectId);
			}
		});

