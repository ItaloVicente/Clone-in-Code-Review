					cache = repository.lockDirCache();
					DirCacheEditor editor = cache.editor();
					editor.add(new PathEdit(gitPath) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.copyMetaData(entry);

							ObjectInserter inserter = repository
									.newObjectInserter();
							ent.copyMetaData(entry);
							ent.setLength(newContent.length);
							ent.setLastModified(System.currentTimeMillis());
							InputStream in = new ByteArrayInputStream(
									newContent);
							try {
								ent.setObjectId(inserter.insert(
										Constants.OBJ_BLOB, newContent.length,
										in));
								inserter.flush();
							} catch (IOException ex) {
								throw new RuntimeException(ex);
							} finally {
								try {
									in.close();
								} catch (IOException e) {
								}
							}
						}
					});
					try {
						editor.commit();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof IOException)
							throw (IOException) e.getCause();
						else
							throw e;
					}

