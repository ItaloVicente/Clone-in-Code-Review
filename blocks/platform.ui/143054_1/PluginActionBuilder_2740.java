			String group = null;
			if (path != null) {
				int loc = path.lastIndexOf('/');
				if (loc != -1) {
					group = path.substring(loc + 1);
					path = path.substring(0, loc);
				} else {
					group = path;
					path = null;
				}
			}

			IMenuManager parent = mng;
			if (path != null) {
				parent = mng.findMenuUsingPath(path);
				if (parent == null) {
