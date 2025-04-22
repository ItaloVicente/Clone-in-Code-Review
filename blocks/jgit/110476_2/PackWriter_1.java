	private void filterAndAddObject(@NonNull RevObject object
			@NonNull ObjectWalk walk
		boolean oversized = false;
		boolean reject = blobMaxBytes >= 0 &&
			object.getType() == OBJ_BLOB &&
			!walk.isGitFile() &&
			(oversized = reader.getObjectSize(object
		if (reject) {
			if (oversized) {
				object.remove(RevFlag.SEEN);
			}
		} else {
			addObject(object
		}
	}

