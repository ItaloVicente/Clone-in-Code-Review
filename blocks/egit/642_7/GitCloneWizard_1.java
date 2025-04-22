		File[] children = f.listFiles();
		if (children != null)
			for (File i : children) {
				if (i.isDirectory()) {
					deleteRecursively(i);
				} else {
					if (!i.delete()) {
						i.deleteOnExit();
					}
