		for (MergeStatus mergeStatus : result.getMergeStatus()) {
			switch (mergeStatus) {
			case ALREADY_UP_TO_DATE:
			case FAST_FORWARD:
				out.println(result.getMergeStatus().toString());
				break;
			case CONFLICTING:
				for (String collidingPath : result.getConflicts().keySet())
					out.println(MessageFormat.format(
							CLIText.get().mergeConflict
				out.println(CLIText.get().mergeFailed);
				break;
			case FAILED:
				for (Map.Entry<String
						.getFailingPaths().entrySet())
					switch (entry.getValue()) {
					case DIRTY_WORKTREE:
					case DIRTY_INDEX:
						out.println(CLIText.get().dontOverwriteLocalChanges);
						out.println("        " + entry.getKey());
						break;
					case COULD_NOT_DELETE:
						out.println(CLIText.get().cannotDeleteFile);
						out.println("        " + entry.getKey());
						break;
					}
				break;
			case MERGED:
				out.println(MessageFormat.format(CLIText.get().mergeMadeBy
						mergeStrategy.getName()));
				break;
			case SQUASHED:
				out.println(CLIText.get().squashCommit);
				break;
			case NOT_SUPPORTED:
				out.println(MessageFormat.format(
						CLIText.get().unsupportedOperation
			}
