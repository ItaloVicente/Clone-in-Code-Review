						Integer.valueOf(commits.size())));

				InteractiveHandler handler = new InteractiveHandler() {
					public void prepareSteps(List<RebaseTodoLine> steps) {
						System.out
								.printf("CherryPickOperation.execute(...).new IWorkspaceRunnable() {...}.run(...).new InteractiveHandler() {...}.prepareSteps()") //$NON-NLS-1$
								.println();

						for (RebaseTodoLine step : steps) {
							try {
								step.setAction(RebaseTodoLine.Action.PICK);
							} catch (IllegalTodoFileModification e) {
							}
						}

						List<RevCommit> stepCommits = new ArrayList<RevCommit>(
								commits);
						Collections.reverse(stepCommits);

						for (RevCommit commit : stepCommits) {
							RebaseTodoLine step = new RebaseTodoLine(
									RebaseTodoLine.Action.PICK,
									commit.abbreviate(7), ""); //$NON-NLS-1$
							steps.add(step);
						}
					}

					public String modifyCommitMessage(String oldMessage) {
						return oldMessage;
					}
				};
