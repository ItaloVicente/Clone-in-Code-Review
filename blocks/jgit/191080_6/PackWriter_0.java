		if (!filterSpec.allowsType(type) && !want.contains(src)) {
			return;
		}

		long blobLimit = filterSpec.getBlobLimit();
		if (blobLimit >= 0 && type == OBJ_BLOB && !want.contains(src)
				&& !reader.isNotLargerThan(src
			return;
