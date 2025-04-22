
						for (CopyFile copyfile : proj.getCopyFiles()) {
							byte[] src = callback.readFile(
									nameUri
							objectId = inserter.insert(Constants.OBJ_BLOB
							dcEntry = new DirCacheEntry(copyfile.dest);
							dcEntry.setObjectId(objectId);
							dcEntry.setFileMode(FileMode.REGULAR_FILE);
							builder.add(dcEntry);
						}
					} else if (!ignoreRemoteFailures) {
						throw new RemoteUnavailableException(nameUri);
