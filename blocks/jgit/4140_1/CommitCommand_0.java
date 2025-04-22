						if (!FileMode.GITLINK.equals(dcEntry.getFileMode())) {
							if (inserter == null)
								inserter = repo.newObjectInserter();

							InputStream inputStream = fTree.openEntryStream();
							try {
								dcEntry.setObjectId(inserter.insert(
										Constants.OBJ_BLOB
										inputStream));
							} finally {
								inputStream.close();
							}
