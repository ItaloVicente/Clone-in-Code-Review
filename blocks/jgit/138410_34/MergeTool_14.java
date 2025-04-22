						new String(executionResult.getStdout().toByteArray()));
				outw.flush();
				errw.println(
						new String(executionResult.getStderr().toByteArray()));
				errw.flush();
			} catch (ToolException e) {
				isMergeSuccessful = false;
				outw.println(e.getResultStdout());
				outw.flush();
				errw.println(MessageFormat.format(
						CLIText.get().mergeToolMergeFailed
				errw.flush();
				if (e.isCommandExecutionError()) {
					errw.println(e.getMessage());
					throw die(CLIText.get().mergeToolExecutionError
				}
			}
			if (isMergeSuccessful) {
				long modifiedAfter = merged.lastModified();
				if (modifiedBefore == modifiedAfter) {
					outw.println(MessageFormat.format(
							CLIText.get().mergeToolFileUnchanged
							mergedFilePath));
					isMergeSuccessful = isMergeSuccessful(showPrompt);
				}
			}
			if (isMergeSuccessful) {
				addFile(mergedFilePath);
			}
		} finally {
			baseSource.close();
			localSource.close();
			remoteSource.close();
		}
		return isMergeSuccessful ? MergeResult.SUCCESSFUL : MergeResult.FAILED;
	}

	private MergeResult mergeDeleted(String mergedFilePath
			throws Exception {
		outw.println(MessageFormat.format(CLIText.get().mergeToolFileUnchanged
				mergedFilePath));
		if (deletedByUs) {
			outw.println(CLIText.get().mergeToolDeletedConflictByUs);
		} else {
			outw.println(CLIText.get().mergeToolDeletedConflictByThem);
		}
		int mergeDecision = getDeletedMergeDecision();
		if (mergeDecision == 1) {
			addFile(mergedFilePath);
		} else if (mergeDecision == -1) {
			rmFile(mergedFilePath);
		} else {
			return MergeResult.ABORTED;
		}
		return MergeResult.SUCCESSFUL;
	}

	private void addFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.add().addFilepattern(fileName).call();
		}
	}

	private void rmFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.rm().addFilepattern(fileName).call();
		}
	}

	private boolean hasUserAccepted(String message) throws IOException {
		boolean yes = true;
		outw.flush();
		BufferedReader br = inputReader;
		String line = null;
		while ((line = br.readLine()) != null) {
				yes = true;
				break;
				yes = false;
