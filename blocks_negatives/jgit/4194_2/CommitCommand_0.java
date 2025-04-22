						if (inserter == null)
							inserter = repo.newObjectInserter();

						InputStream inputStream = fTree.openEntryStream();
						try {
							dcEntry.setObjectId(inserter.insert(
									Constants.OBJ_BLOB, entryLength,
									inputStream));
						} finally {
							inputStream.close();
