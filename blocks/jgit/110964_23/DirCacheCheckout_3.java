	private void keep(DirCacheEntry entry) throws IOException {
		if (entry != null && !FileMode.TREE.equals(entry.getFileMode())) {
			if (entry.isSkipWorkTree()) {
				if (!skipSparse(entry)
						&& (!workingTree.findFile(entry.getPathString())
								|| !workingTree.isModified(entry
										this.walk.getObjectReader()))) {
					entry.setSkipWorkTree(false);
					updated.put(entry.getPathString()
							walk.getEolStreamType(CHECKOUT_OP)
							walk.getFilterCommand(
									Constants.ATTR_FILTER_TYPE_SMUDGE)));
				}
			} else {
				if (skipSparse(entry)) {
					entry.setSkipWorkTree(true);
					removed.add(entry.getPathString());
				}

			}
			builder.add(entry);
		}
