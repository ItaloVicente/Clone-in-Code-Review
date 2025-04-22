		final List<ObjectId> packedObjs = pw.getObjectList();
		newPackObj.add(new PackWriter.ObjectIdSet() {
			public boolean contains(AnyObjectId objectId) {
				return 0 <= Collections.binarySearch(packedObjs
			}
		});

