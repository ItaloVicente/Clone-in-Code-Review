							ent.setFileMode(mode);
							try {
								DirCacheCheckout.checkoutEntry(repo
										workTree
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
