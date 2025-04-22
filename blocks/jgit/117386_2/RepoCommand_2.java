									Constants.CHARACTER_ENCODING));
							dcEntry = new DirCacheEntry(linkfile.dest);
							dcEntry.setObjectId(objectId);
							dcEntry.setFileMode(FileMode.SYMLINK);
							builder.add(dcEntry);
						}
