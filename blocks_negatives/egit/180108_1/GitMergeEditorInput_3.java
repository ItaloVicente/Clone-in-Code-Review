					((EditableRevision) left)
							.addContentChangeListener(source -> {
								DirCache cache = null;
								try {
									cache = repository.lockDirCache();
									DirCacheEditor editor = cache.editor();
									editor.add(new PathEdit(gitPath) {

										private boolean done;

										@Override
										public void apply(DirCacheEntry ent) {
											if (!done && ent.getStage() > 0) {
												ent.setLastModified(
														Instant.now());
												done = true;
											}
										}
									});
									editor.commit();
								} catch (RuntimeException | IOException e) {
									Activator.logError(MessageFormat.format(
											UIText.GitMergeEditorInput_ErrorUpdatingIndex,
											gitPath), e);
								} finally {
									if (cache != null) {
										cache.unlock();
									}
								}
							});
