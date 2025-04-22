	public static Path writeLink(Repository db
			String target) throws Exception {
		return FileUtils.createSymLink(new File(db.getWorkTree()
				target);
	}

