							for (RevCommit commit : stepCommits) {
								RebaseTodoLine step = new RebaseTodoLine(
										RebaseTodoLine.Action.PICK,
										commit.abbreviate(7), ""); //$NON-NLS-1$
								steps.add(step);
							}
