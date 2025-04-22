	}

	private void compare(List<DiffEntry> files
			String toolNamePrompt) throws IOException {
		ContentSource.Pair sourcePair = new ContentSource.Pair(source(oldTree)
				source(newTree));
		try {
			for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
				DiffEntry ent = files.get(fileIndex);
				String mergedFilePath = ent.getNewPath();
				if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
					mergedFilePath = ent.getOldPath();
				}
				FileElement local = new FileElement(ent.getOldPath()
						ent.getOldId().name()
						getObjectStream(sourcePair
				FileElement remote = new FileElement(ent.getNewPath()
						ent.getNewId().name()
						getObjectStream(sourcePair
				boolean launchCompare = true;
				if (showPrompt) {
					launchCompare = isLaunchCompare(fileIndex + 1
							mergedFilePath
				}
				if (launchCompare) {
					try {
						ExecutionResult result = diffToolMgr.compare(db
								remote
								toolName
						outw.println(new String(result.getStdout().toByteArray()));
						errw.println(
								new String(result.getStderr().toByteArray()));
					} catch (ToolException e) {
						outw.println(e.getResultStdout());
						outw.flush();
						errw.println(e.getMessage());
						throw die("external diff died
								+ mergedFilePath
					}
				} else {
					break;
				}
			}
		} finally {
			sourcePair.close();
		}
	}

	private boolean isLaunchCompare(int fileIndex
			String fileName
		boolean launchCompare = true;
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
			}
		}
		return launchCompare;
	}
