		ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> objs = new ObjectIdOwnerMap<
				ObjectIdOwnerMap.Entry>();
		for (BlockList<ObjectToPack> objList : objectsLists) {
			if (objList != null) {
				for (ObjectToPack otp : objList)
					objs.add(new ObjectIdOwnerMap.Entry(otp) {
					});
			}
		}
		return objs;
