				monitor.beginTask(MessageFormat.format(
						JGitText.get().applyingCommit, commitToPick
								.getShortMessage()), ProgressMonitor.UNKNOWN);
				newHead = tryFastForward(commitToPick);
				lastStepWasForward = newHead != null;
				if (!lastStepWasForward)
					newHead = new Git(repo).cherryPick().include(commitToPick)
							.call().getNewHead();
				monitor.endTask();
				if (newHead == null) {
					return stop(commitToPick);
