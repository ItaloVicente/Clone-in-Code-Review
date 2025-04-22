	public synchronized Repository getRepository(IResource res) {
		IPath projectRelativePath = res.getProjectRelativePath();
		if (projectRelativePath == null)
			return db;
		String projectRelativePathStr = res.getProjectRelativePath().toString();
		try {
			if (SubmoduleWalk.containsGitModulesFile(db)) {
				SubmoduleWalk sw = SubmoduleWalk.forIndex(db);
				while (sw.next())
					if (projectRelativePathStr.startsWith(sw.getPath()))
						return sw.getRepository();
			}
		} catch (IOException e) {
		}
		return db;
	}

