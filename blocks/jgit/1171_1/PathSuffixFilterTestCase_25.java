		final ObjectInserter odi = db.newObjectInserter();
		final ObjectId aSth = odi.insert(OBJ_BLOB
		final ObjectId aTxt = odi.insert(OBJ_BLOB
		final ObjectId bSth = odi.insert(OBJ_BLOB
		final ObjectId bTxt = odi.insert(OBJ_BLOB
		final DirCache dc = db.readDirCache();
