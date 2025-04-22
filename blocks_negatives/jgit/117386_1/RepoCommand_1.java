					DirCacheEntry dcEntry = new DirCacheEntry(path);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(FileMode.GITLINK);
					builder.add(dcEntry);

					for (CopyFile copyfile : proj.getCopyFiles()) {
						byte[] src = callback.readFile(
								nameUri, proj.getRevision(), copyfile.src);
						objectId = inserter.insert(Constants.OBJ_BLOB, src);
						dcEntry = new DirCacheEntry(copyfile.dest);
