		switch (result.getMergeStatus()) {
		case ALREADY_UP_TO_DATE:
			if (squash)
				outw.print(CLIText.get().nothingToSquash);
			outw.println(CLIText.get().alreadyUpToDate);
			break;
		case FAST_FORWARD:
			ObjectId oldHeadId = oldHead.getObjectId();
			if (oldHeadId != null) {
				String oldId = oldHeadId.abbreviate(7).name();
				String newId = result.getNewHead().abbreviate(7).name();
				outw.println(MessageFormat.format(CLIText.get().updating, oldId,
						newId));
			}
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
