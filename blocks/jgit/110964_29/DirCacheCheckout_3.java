	private void keep(DirCacheEntry entry) throws IOException {
		if (entry != null && !FileMode.TREE.equals(entry.getFileMode())) {
			if (entry.isSkipWorkTree()) {

				boolean isEntryModified = entry.getLastModified() != 0
						&& workingTree.findFile(entry.getPathString())
						&& workingTree.isModified(entry
								this.walk.getObjectReader());

				if (skipSparse(entry)) {
					if (!isEntryModified) {
						entry.setSkipWorkTree(true);
						removed.add(entry.getPathString());
					}
				} else {
					if (!isEntryModified) {
						entry.setSkipWorkTree(false);

						if (entry.isMerged()) {
							updated.put(entry.getPathString()
									new CheckoutMetadata(
											walk.getEolStreamType(CHECKOUT_OP)
											walk.getFilterCommand(
													Constants.ATTR_FILTER_TYPE_SMUDGE)));
						} else {
							removed.add(entry.getPathString());
						}
					}
				}
			} else {
				if (skipSparse(entry)) {
					entry.setSkipWorkTree(true);
					removed.add(entry.getPathString());
				}
			}
			builder.add(entry);
		}
