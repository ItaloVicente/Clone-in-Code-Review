			DirCacheEntry extraDcEntry = new DirCacheEntry(ec.path);

			ObjectId oid = inserter.insert(Constants.OBJ_BLOB,
					ec.content.getBytes(UTF_8));
			extraDcEntry.setObjectId(oid);
			extraDcEntry.setFileMode(FileMode.REGULAR_FILE);
			builder.add(extraDcEntry);
