		Set<File> d = new TreeSet<File>();
		erase(projectName0, d);
		erase(projectName1, d);
		erase(projectName2, d);
		erase(projectName3, d);
		for (File f : d)
			if (f.exists())
				FileUtils.delete(f, FileUtils.RECURSIVE);
