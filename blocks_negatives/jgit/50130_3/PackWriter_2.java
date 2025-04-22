		long bitmapIndexMisses;

		long totalDeltas;

		long reusedObjects;

		long reusedDeltas;

		long totalBytes;

		long thinPackBytes;

		long timeCounting;

		long timeSearchingForReuse;

		long timeSearchingForSizes;

		long timeCompressing;

		long timeWriting;

		ObjectType[] objectTypes;

		{
			objectTypes = new ObjectType[5];
			objectTypes[OBJ_COMMIT] = new ObjectType();
			objectTypes[OBJ_TREE] = new ObjectType();
			objectTypes[OBJ_BLOB] = new ObjectType();
			objectTypes[OBJ_TAG] = new ObjectType();
