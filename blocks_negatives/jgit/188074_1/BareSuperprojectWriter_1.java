					objectId = inserter.insert(Constants.OBJ_BLOB,
							link.getBytes(UTF_8));
					dcEntry = new DirCacheEntry(linkfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(FileMode.SYMLINK);
					builder.add(dcEntry);
