	private void checkParameters() throws InvalidMergeHeadsException {
		if (squash && fastForwardMode == FastForwardMode.NO_FF) {
			throw new JGitInternalException(
					JGitText.get().cannotCombineSquashWithNoff);
		}

		if (commits.size() != 1)
			throw new InvalidMergeHeadsException(
					commits.isEmpty() ? JGitText.get().noMergeHeadSpecified
							: MessageFormat.format(
									JGitText.get().mergeStrategyDoesNotSupportHeads
									mergeStrategy.getName()
									Integer.valueOf(commits.size())));
	}

