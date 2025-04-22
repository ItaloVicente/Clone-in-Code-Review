		final ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> packedObjs = new ObjectIdOwnerMap<
				ObjectIdOwnerMap.Entry>();
		for (ObjectId obj : pw.getObjectList()) {
			packedObjs.add(new ObjectIdOwnerMap.Entry(obj) {
			});
		}
