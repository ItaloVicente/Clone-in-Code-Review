	private void keep(DirCacheEntry entry) throws IOException {
		if (entry != null && !FileMode.TREE.equals(entry.getFileMode())) {
			if (isSparseCheckout) {
				CanonicalTreeParser m;

				if (walk.getTreeCount() == 3) {
					m = walk.getTree(0
				} else {
					m = walk.getTree(1
				}

				boolean isEntryModified = entry.getLastModified() != 0
						&& workingTree.findFile(entry.getPathString())
						&& workingTree.isModified(entry
								this.walk.getObjectReader());

				if (sparseCheckoutFileExists) {
					boolean skipSparse = skipSparse(entry.getPathString());

					if (skipSparse) {
						if (m == null || !isEntryModified) {
							removed.add(entry.getPathString());
						}

						if (m != null) {
							builder.add(entry);
						}
					} else {
						if (m != null) {
							if (force || !force && !entry.isSkipWorkTree()) {
								entry.setSkipWorkTree(false);
								builder.add(entry);
							} else {
								conflicts.add(entry.getPathString());
							}
						} else {
							if (!entry.isSkipWorkTree()) {
								removed.add(entry.getPathString());
							} else {
								if (workingTree
										.findFile(entry.getPathString())) {
									conflicts.add(entry.getPathString());
									builder.add(entry);
								}
							}
						}
					}

					if (skipSparse && !entry.isSkipWorkTree()) {
						entry.setSkipWorkTree(true);
					}
				} else {
					if (m == null) {
						if (!entry.isSkipWorkTree()) {
							if (force) {
								removed.add(entry.getPathString());
							} else {
								builder.add(entry);
							}
						} else {
							if (workingTree.findFile(entry.getPathString())) {
								if (force) {
									conflicts.add(entry.getPathString());
								}
								builder.add(entry);
							} else {
								if (!force) {
									builder.add(entry);
								}
							}
						}
					} else {
						builder.add(entry);
					}
				}
			} else {
				builder.add(entry);

			}
		}
