
					for (CopyFile copyfile : proj.copyfiles) {
						ReadFileResult result = callback.readFile(
								uri
						objectId = inserter.insert(Constants.OBJ_BLOB
								result.size
						dcEntry = new DirCacheEntry(
								copyfile.dest);
						dcEntry.setObjectId(objectId);
						dcEntry.setFileMode(FileMode.REGULAR_FILE);
						builder.add(dcEntry);
					}
