				if (!MergeInputMode.WORKTREE.equals(mode)) {
					config.setChangeIgnored(
							config.isMirrored() ? RangeDifference.RIGHT
									: RangeDifference.LEFT,
							true);
					config.setChangeIgnored(RangeDifference.ANCESTOR, true);
				}
