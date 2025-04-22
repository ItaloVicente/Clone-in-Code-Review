
			if (isForCheckout) {
				updated.put(path
						walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
			} else {
				entry.setSkipWorkTree(true);
				removed.add(path);
			}
