			perFolder = pruneFolderMap(perFolder, progress.newChild(1));
			if (perFolder == null) {
				return;
			}

			updateGitIgnores(perFolder, progress.newChild(1));
