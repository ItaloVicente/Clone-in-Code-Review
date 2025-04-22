	public ObjectWalk toObjectWalkWithSameObjects() {
		ObjectWalk ow = new ObjectWalk(reader);
		RevWalk rw = ow;
		rw.objects = objects;
		rw.freeFlags = freeFlags;
		return ow;
	}

