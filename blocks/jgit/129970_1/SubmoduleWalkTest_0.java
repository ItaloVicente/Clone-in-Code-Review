		File modulesGitDir = new File(db.getDirectory()
				"modules" + File.separatorChar + path);
		try (BufferedWriter fw = Files.newBufferedWriter(dotGit.toPath()
				UTF_8)) {
			fw.append("gitdir: " + modulesGitDir.getAbsolutePath());
		}
