		if ((depth != 0) && !(resolved != null && isSameFile(root, resolved))) {
			File[] children = root.listFiles();
			if (children == null) {
				return;
			}
			for (File child : children) {
				if (monitor.isCanceled()) {
					return;
