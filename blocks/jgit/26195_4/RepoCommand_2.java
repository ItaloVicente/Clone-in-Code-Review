
					for (CopyFile copyfile : proj.copyfiles) {
						byte[] src = callback.readFile(
								uri
						objectId = inserter.insert(Constants.OBJ_BLOB
						dcEntry = new DirCacheEntry(copyfile.dest);
						dcEntry.setObjectId(objectId);
						dcEntry.setFileMode(FileMode.REGULAR_FILE);
						builder.add(dcEntry);
					}
