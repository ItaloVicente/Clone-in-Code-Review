
							InputStream in = f.openEntryStream();
							try {
								entry.setObjectId(inserter.insert(
										Constants.OBJ_BLOB
							} finally {
								in.close();
							}
