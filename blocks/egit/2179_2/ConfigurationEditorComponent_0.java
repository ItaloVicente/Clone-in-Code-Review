	private boolean isWriteable(final File f) {
		if (f.exists())
			if (f.isFile())
				if (f.canWrite())
					return true;
				else
					return false;
			else
				return false;
		for (File d = f.getParentFile(); d != null; d = d.getParentFile()) {
			if (d.isDirectory())
				if (d.canWrite())
					return true;
				else
					return false;
			else
				if (d.exists())
					return false;
		}
		return false;
	}

