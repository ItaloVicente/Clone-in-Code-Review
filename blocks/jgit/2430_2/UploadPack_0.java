
				if (obj instanceof RevCommit) {
					RevCommit c = (RevCommit) obj;
					if (oldestTime == 0 || c.getCommitTime() < oldestTime)
						oldestTime = c.getCommitTime();
				}

