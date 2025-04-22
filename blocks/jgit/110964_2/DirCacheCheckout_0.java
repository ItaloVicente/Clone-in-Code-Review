			if (skipSparse(entry)) {
				entry.setSkipWorkTree(true);
				removed.add(path);
			} else {
				updated.put(path
						walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
			}
