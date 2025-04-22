				try {
					monitor.beginTask(MessageFormat.format(
							JGitText.get().applyingCommit
							commitToPick.getShortMessage())
							ProgressMonitor.UNKNOWN);
					newHead = tryFastForward(commitToPick);
					lastStepWasForward = newHead != null;
					if (!lastStepWasForward) {
						CherryPickResult cherryPickResult = new Git(repo)
								.cherryPick().include(commitToPick).call();
						switch (cherryPickResult.getStatus()) {
						case FAILED:
							if (operation == Operation.BEGIN)
								return abort(new RebaseResult(
										cherryPickResult.getFailingPaths()));
							else
								return stop(commitToPick);
						case CONFLICTING:
							return stop(commitToPick);
						case OK:
							newHead = cherryPickResult.getNewHead();
						}
					}
				} finally {
					monitor.endTask();
