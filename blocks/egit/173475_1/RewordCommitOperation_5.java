					commits.push(c);
				}
				if (c == null) {
					throw new TeamException(MessageFormat.format(
							CoreText.RewordCommitOperation_notReachable,
							Utils.getShortObjectId(commit)));
