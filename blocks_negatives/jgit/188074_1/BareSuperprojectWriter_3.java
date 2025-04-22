			DirCacheEntry dcEntryAttr = new DirCacheEntry(
					Constants.DOT_GIT_ATTRIBUTES);
			ObjectId attrId = inserter.insert(Constants.OBJ_BLOB,
					attributes.toString().getBytes(UTF_8));
			dcEntryAttr.setObjectId(attrId);
			dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
			builder.add(dcEntryAttr);
