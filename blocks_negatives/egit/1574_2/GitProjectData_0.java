		if (d == null) {
			try {
				d = new GitProjectData(p).load();
			} catch (IOException ioe) {
				d = new GitProjectData(p);
			}
		}
		d.delete();
