				if (recordSubmoduleLabels) {
					final DirCacheEntry dcEntryAttr = new DirCacheEntry(Constants.DOT_GIT_ATTRIBUTES);
					ObjectId attrId = inserter.insert(Constants.OBJ_BLOB
							attributes.toString().getBytes(Constants.CHARACTER_ENCODING));
					dcEntryAttr.setObjectId(attrId);
					dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
					builder.add(dcEntryAttr);
				}

