			File[] children = root.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isDirectory()) {
						collectProjectDirectories(res, child, monitor);
					}
