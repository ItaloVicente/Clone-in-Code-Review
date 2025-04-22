
		if (commits.size() != 1)
			throw new InvalidMergeHeadsException(
					commits.isEmpty() ? JGitText.get().noMergeHeadSpecified
							: MessageFormat.format(
									JGitText.get().mergeStrategyDoesNotSupportHeads,
									mergeStrategy.getName(),
									Integer.valueOf(commits.size())));
