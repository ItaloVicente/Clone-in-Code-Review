				if (FileKey.isGitRepository(dir, tryFS)) {
					setGitDir(dir);
					break;
				} else if (dir.isFile()) {
					try {
						setGitDir(getSymRef(current, dir, tryFS));
