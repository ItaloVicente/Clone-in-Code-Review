		if (getGitDir() == null) {
			FS tryFS = safeFS();
			while (current != null) {
				File dir = new File(current, DOT_GIT);
				if (FileKey.isGitRepository(dir, tryFS)) {
					setGitDir(dir);
					break;
				} else if (dir.isFile()) {
					try {
						setGitDir(getSymRef(current, dir, tryFS));
						break;
					} catch (IOException ignored) {
					}
				} else if (FileKey.isGitRepository(current, tryFS)) {
					setGitDir(current);
					break;
				}

				current = current.getParentFile();
				if (current != null && ceilingDirectories != null
						&& ceilingDirectories.contains(current))
					break;
			}
		}
		return self();
