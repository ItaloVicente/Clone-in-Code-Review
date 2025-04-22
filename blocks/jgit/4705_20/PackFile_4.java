	public boolean shouldBeKept() {
		if (keepFile == null)
			keepFile = new File(packFile.getPath() + ".keep");
		return keepFile.exists();
	}

