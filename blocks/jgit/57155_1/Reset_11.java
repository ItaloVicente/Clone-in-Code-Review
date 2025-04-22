		try (Git git = new Git(db)) {
			ResetCommand command = git.reset();
			command.setRef(commit);
			ResetType mode = null;
			if (soft)
				mode = selectMode(mode
			if (mixed)
				mode = selectMode(mode
			if (hard)
				mode = selectMode(mode
			if (mode == null)
				throw die("no reset mode set");
			command.setMode(mode);
			command.call();
		}
