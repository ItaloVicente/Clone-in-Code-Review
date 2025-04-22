									entry.setLength(sz);
									entry.setLastModified(f
											.getEntryLastModified());
									long contentSize = f
											.getEntryContentLength();
									InputStream in = f.openEntryStream();
									try {
										entry.setObjectId(inserter.insert(
												Constants.OBJ_BLOB, contentSize, in));
									} finally {
										in.close();
									}
								} else
