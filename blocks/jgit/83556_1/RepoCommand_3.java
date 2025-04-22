					for (LinkFile linkfile : proj.getLinkFiles()) {
						byte[] src = callback.readFile(
								nameUri
						objectId = inserter.insert(Constants.OBJ_BLOB
						dcEntry = new DirCacheEntry(linkfile.dest);
						dcEntry.setObjectId(objectId);
						dcEntry.setFileMode(FileMode.REGULAR_FILE);
						builder.add(dcEntry);
					}
