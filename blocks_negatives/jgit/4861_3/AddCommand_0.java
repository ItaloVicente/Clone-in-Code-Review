								entry.setLength(sz);
								entry.setLastModified(f.getEntryLastModified());
								entry.setFileMode(f.getEntryFileMode());

								InputStream in = f.openEntryStream();
								try {
									entry.setObjectId(inserter.insert(
											Constants.OBJ_BLOB, sz, in));
								} finally {
									in.close();
