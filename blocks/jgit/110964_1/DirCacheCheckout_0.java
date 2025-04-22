		if (e != null && !FileMode.TREE.equals(e.getFileMode())) {
			boolean isForCheckout = isForCheckout(e.getPathString());

			if (!isForCheckout) {
				e.setSkipWorkTree(true);
				removed.add(e.getPathString());
			}
