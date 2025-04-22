	private void filterAndAddObject(@NonNull RevObject object
			@NonNull ObjectWalk walk
		boolean reject = blobMaxBytes >= 0 &&
			object.getType() == OBJ_BLOB &&
			!walk.isGitSpecialFile() &&
			reader.getObjectSize(object
		if (reject) {
			object.remove(RevFlag.SEEN);
		} else {
			addObject(object
		}
	}

