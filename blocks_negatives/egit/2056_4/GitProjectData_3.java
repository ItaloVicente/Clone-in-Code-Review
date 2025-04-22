		final File[] todel = dir.listFiles();
		if (todel != null) {
			for (int k = 0; k < todel.length; k++) {
				if (todel[k].isFile()) {
					todel[k].delete();
				}
			}
		}
		dir.delete();
				+ project.getName()
