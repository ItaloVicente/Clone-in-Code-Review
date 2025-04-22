		try {
			final Ref srcRef = db.findRef(ref);
			if (src == null)
				throw die(MessageFormat
						.format(CLIText.get().refDoesNotExistOrNoCommit

			Ref oldHead = getOldHead();
			MergeResult result;
			try (Git git = new Git(db)) {
				MergeCommand mergeCmd = git.merge().setStrategy(mergeStrategy)
						.setSquash(squash).setFastForward(ff)
						.setCommit(!noCommit);
				if (srcRef != null)
					mergeCmd.include(srcRef);
				else
					mergeCmd.include(src);

				if (message != null)
					mergeCmd.setMessage(message);

				try {
					result = mergeCmd.call();
				} catch (CheckoutConflictException e) {
				}
