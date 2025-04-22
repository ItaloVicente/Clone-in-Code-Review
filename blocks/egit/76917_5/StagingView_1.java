				Set<String> commitHistory = new LinkedHashSet<>();
				if (overwrittenCommitMessage != null) {
					commitHistory.add(overwrittenCommitMessage);
				}
				commitHistory.addAll(CommitMessageHistory.getCommitHistory());
				return commitHistory;
