	public CommitCommand setOnly(String only) {
		checkCallable();
		if (all)
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
					"--all"));
		String o = only.endsWith("/") ? only.substring(0
				: only;
		if (!this.only.contains(o))
			this.only.add(o);
		return this;
	}
