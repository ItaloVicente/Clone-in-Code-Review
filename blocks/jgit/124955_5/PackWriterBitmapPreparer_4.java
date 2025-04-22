					if (selectionHelper.newWants.remove(c)) {
						if (nextIn > 0) {
							nextFlg = 0;
						}
					} else {
						boolean stillInSpan = nextIn >= 0;
						boolean isMergeCommit = c.getParentCount() > 1;
						boolean mustPick = (nextIn <= -recentCommitSpan)
								|| (isActiveBranch
										&& (distanceFromTip <= contiguousCommitCount))
						if (!mustPick && (stillInSpan || !isMergeCommit)) {
							continue;
