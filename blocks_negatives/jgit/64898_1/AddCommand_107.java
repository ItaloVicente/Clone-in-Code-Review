				else if (!(path.equals(lastAddedFile))) {
					if (!(update && tw.getTree(0, DirCacheIterator.class) == null)) {
						c = tw.getTree(0, DirCacheIterator.class);
							long sz = f.getEntryLength();
							DirCacheEntry entry = new DirCacheEntry(path);
							if (c == null || c.getDirCacheEntry() == null
									|| !c.getDirCacheEntry().isAssumeValid()) {
								FileMode mode = f.getIndexFileMode(c);
								entry.setFileMode(mode);

								if (FileMode.GITLINK != mode) {
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
									entry.setObjectId(f.getEntryObjectId());
								builder.add(entry);
								lastAddedFile = path;
							} else {
								builder.add(c.getDirCacheEntry());
							}

						} else if (c != null
								&& (!update || FileMode.GITLINK == c
										.getEntryFileMode()))
							builder.add(c.getDirCacheEntry());
