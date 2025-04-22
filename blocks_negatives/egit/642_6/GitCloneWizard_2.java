		for (File i : f.listFiles()) {
			if (i.isDirectory()) {
				deleteRecursively(i);
			} else {
				if (!i.delete()) {
					i.deleteOnExit();
