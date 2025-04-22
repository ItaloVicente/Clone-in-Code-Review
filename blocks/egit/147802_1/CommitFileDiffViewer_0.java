				if (input.isFirstParentOnly()
						&& input.getCommit().getParentCount() > 1) {
					RevCommit[] parents = { input.getCommit().getParent(0) };
					diffs = FileDiff.compute(input.getRepository(),
							input.getTreeWalk(), input.getCommit(), parents,
							monitor, filter);
				} else {
					diffs = FileDiff.compute(input.getRepository(),
							input.getTreeWalk(), input.getCommit(), monitor,
							filter);
				}
