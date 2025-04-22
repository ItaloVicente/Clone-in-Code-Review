		boolean reject =
			(!filterSpec.allowsType(type) && !want.contains(src)) ||
			(filterSpec.getBlobLimit() >= 0 &&
				type == OBJ_BLOB &&
				!want.contains(src) &&
				reader.getObjectSize(src
