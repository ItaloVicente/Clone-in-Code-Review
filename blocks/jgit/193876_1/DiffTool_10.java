		ContentSource.Pair sourcePair = new ContentSource.Pair(source(oldTree)
				source(newTree));
		try {
			for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
				DiffEntry ent = files.get(fileIndex);
				String mergedFilePath = ent.getNewPath();
				if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
					mergedFilePath = ent.getOldPath();
				}
				boolean launchCompare = true;
				if (showPrompt) {
					launchCompare = isLaunchCompare(fileIndex + 1
							mergedFilePath
				}
				if (launchCompare) {
					try {
						FileElement local = createFileElement(
								FileElement.Type.LOCAL
								ent);
						FileElement remote = createFileElement(
								FileElement.Type.REMOTE
								ent);
						FileElement merged = new FileElement(mergedFilePath
								FileElement.Type.MERGED);
						ExecutionResult result = diffTools.compare(local
								remote
								trustExitCode);
						outw.println(new String(result.getStdout().toByteArray()));
						errw.println(
								new String(result.getStderr().toByteArray()));
					} catch (ToolException e) {
						outw.println(e.getResultStdout());
						outw.flush();
						errw.println(e.getMessage());
