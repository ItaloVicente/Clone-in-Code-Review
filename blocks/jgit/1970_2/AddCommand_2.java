							if (c == null || c.getDirCacheEntry() == null
									|| !c.getDirCacheEntry().isAssumeValid()) {
								entry.setLength(sz);
								entry.setLastModified(f.getEntryLastModified());
								entry.setFileMode(f.getEntryFileMode());

								InputStream in = f.openEntryStream();
								try {
									entry.setObjectId(inserter.insert(
											Constants.OBJ_BLOB
								} finally {
									in.close();
								}

								builder.add(entry);
								lastAddedFile = path;
							} else {
								builder.add(c.getDirCacheEntry());
