						} else {
							if (FileMode.GITLINK.equals(dcEntry.getFileMode()))
								dcEntry.setObjectId(fTree.getEntryObjectId());
							else {
								if (inserter == null)
									inserter = repo.newObjectInserter();
								long contentLength = fTree
										.getEntryContentLength();
								InputStream inputStream = fTree
										.openEntryStream();
								try {
									dcEntry.setObjectId(inserter.insert(
											Constants.OBJ_BLOB
											inputStream));
								} finally {
									inputStream.close();
								}
