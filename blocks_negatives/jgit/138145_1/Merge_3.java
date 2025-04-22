			outw.println(result.getMergeStatus().toString());
			break;
		case CHECKOUT_CONFLICT:
			outw.println(CLIText.get().mergeCheckoutConflict);
			for (String collidingPath : result.getCheckoutConflicts())
			outw.println(CLIText.get().mergeCheckoutFailed);
			break;
		case CONFLICTING:
			for (String collidingPath : result.getConflicts().keySet())
				outw.println(MessageFormat.format(CLIText.get().mergeConflict,
						collidingPath));
			outw.println(CLIText.get().mergeFailed);
			break;
		case FAILED:
			for (Map.Entry<String, MergeFailureReason> entry : result
					.getFailingPaths().entrySet())
				switch (entry.getValue()) {
				case DIRTY_WORKTREE:
				case DIRTY_INDEX:
					outw.println(CLIText.get().dontOverwriteLocalChanges);
					break;
				case COULD_NOT_DELETE:
					outw.println(CLIText.get().cannotDeleteFile);
					break;
