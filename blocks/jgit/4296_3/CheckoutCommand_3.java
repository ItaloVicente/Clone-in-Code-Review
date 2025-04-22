							ent.setFileMode(mode);
							try {
								DirCacheCheckout.checkoutEntry(repo
										workTree
							} catch (IOException e) {
								throw new JGitInternalException(
										MessageFormat.format(
												JGitText.get().checkoutConflictWithFile
												ent.getPathString())
							}
