
		@Override
		public ObjectWalk toObjectWalkWithSameObjects() {
			ObjectWalk ow = new ObjectWalk(reader
			ow.objects = objects;
			ow.freeFlags = freeFlags;
			return ow;
		}
