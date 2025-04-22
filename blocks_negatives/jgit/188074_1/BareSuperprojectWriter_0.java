					objectId = inserter.insert(Constants.OBJ_BLOB,
							rf.getContents());
					dcEntry = new DirCacheEntry(copyfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(rf.getFileMode());
					builder.add(dcEntry);
