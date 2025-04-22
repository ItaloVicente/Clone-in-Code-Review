		ObjectTypeAndSize info = new ObjectTypeAndSize();

		for (PackedObjectInfo obj : deferredCheckBlobs) {
			info = openDatabase(obj, info);

			if (info.type != Constants.OBJ_BLOB)
