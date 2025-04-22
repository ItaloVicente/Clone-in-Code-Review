					try {
						RevCommit commit = rw.parseCommit(id);
						compareString = commit.getId().name();
					} finally {
						rw.release();
					}
