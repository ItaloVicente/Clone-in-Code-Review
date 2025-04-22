				DirCache cache = null;
				try {
					cache = repository.lockDirCache();
					DirCacheEditor editor = cache.editor();
					if (newContent.length == 0)
						editor.add(new DirCacheEditor.DeletePath(gitPath));
					else
						editor.add(new DirCacheEntryEditor(gitPath,
								repository, entry, newContent));
					try {
						editor.commit();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof IOException)
							throw (IOException) e.getCause();
						else
							throw e;
					}

				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithIndexAction_errorOnAddToIndex, e,
							true);
				} finally {
					if (cache != null)
						cache.unlock();
				}
