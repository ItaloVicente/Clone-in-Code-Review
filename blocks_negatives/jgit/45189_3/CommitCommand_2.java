						else {
							if (inserter == null)
								inserter = repo.newObjectInserter();
							long contentLength = fTree.getEntryContentLength();
							InputStream inputStream = fTree.openEntryStream();
							try {
								dcEntry.setObjectId(inserter.insert(
										Constants.OBJ_BLOB, contentLength,
										inputStream));
							} finally {
								inputStream.close();
