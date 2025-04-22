					ref = rw.parseAny(ref);
					ref = peelTag(rw
					if (ref instanceof RevCommit) {
						RevCommit commit = ((RevCommit) ref);
						if (commit.getParentCount() == 0)
							ref = null;
