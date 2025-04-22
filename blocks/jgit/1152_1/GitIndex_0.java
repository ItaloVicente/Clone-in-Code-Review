			if (cacheFile.exists()) {
				if (runsOnWindows()) {
					boolean deleted = false;
					for (int i = 0; i < 10; i++) {
						if (cacheFile.delete()) {
							deleted = true;
							break;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
					if (!deleted)
						throw new IOException(
								JGitText.get().couldNotRenameDeleteOldIndex);
				} else {
					if (!cacheFile.delete())
						throw new IOException(
								JGitText.get().couldNotRenameDeleteOldIndex);
				}
			}
