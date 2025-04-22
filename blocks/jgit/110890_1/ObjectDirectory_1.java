	void closeAllPackHandles(File packFile) {
		if (packFile.exists()) {
			for (PackFile p : getPacks()) {
				if (packFile.getPath().equals(p.getPackFile().getPath())) {
					p.close();
					break;
				}
			}
		}
	}

