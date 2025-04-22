			}
			if (only && all) {
				throw die(CLIText.get().onlyOneCommitOptionAllowed);
			}
			if (!paths.isEmpty()) {
				for (String p : paths) {
