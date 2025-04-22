				if (Action.COMMENT.equals(step.getAction()))
					continue;
				Collection<ObjectId> ids = or.resolve(step.getCommit());
				if (ids.size() != 1)
					throw new JGitInternalException(
							"Could not resolve uniquely the abbreviated object ID");
				RevCommit commitToPick = walk
						.parseCommit(ids.iterator().next());
				if (monitor.isCancelled())
					return new RebaseResult(commitToPick, Status.STOPPED);
				try {
					monitor.beginTask(MessageFormat.format(
							JGitText.get().applyingCommit,
							commitToPick.getShortMessage()),
							ProgressMonitor.UNKNOWN);
					newHead = tryFastForward(commitToPick);
					lastStepWasForward = newHead != null;
					if (!lastStepWasForward) {
						String ourCommitName = getOurCommitName();
						CherryPickResult cherryPickResult = new Git(repo)
								.cherryPick().include(commitToPick)
								.setOurCommitName(ourCommitName)
						switch (cherryPickResult.getStatus()) {
						case FAILED:
							if (operation == Operation.BEGIN)
								return abort(new RebaseResult(
										cherryPickResult.getFailingPaths()));
							else
								return stop(commitToPick, Status.STOPPED);
						case CONFLICTING:
							return stop(commitToPick, Status.STOPPED);
						case OK:
							newHead = cherryPickResult.getNewHead();
						}
					}
					boolean isSquash = false;
					switch (step.getAction()) {
					case PICK:
					case REWORD:
						String oldMessage = commitToPick.getFullMessage();
						String newMessage = interactiveHandler
								.modifyCommitMessage(oldMessage);
						newHead = new Git(repo).commit().setMessage(newMessage)
								.setAmend(true).call();
						continue;
					case EDIT:
						rebaseState.createFile(AMEND, commitToPick.name());
						return stop(commitToPick, Status.EDIT);
					case COMMENT:
						break;
					case SQUASH:
						isSquash = true;
					case FIXUP:
						resetSoftToParent();
						RebaseTodoLine nextStep = (i >= steps.size() - 1 ? null
								: steps.get(i + 1));
						File messageFixupFile = rebaseState.getFile(MESSAGE_FIXUP);
						File messageSquashFile = rebaseState
								.getFile(MESSAGE_SQUASH);
						if (isSquash && messageFixupFile.exists())
								messageFixupFile.delete();
						newHead = doSquashFixup(isSquash, commitToPick,
								nextStep, messageFixupFile, messageSquashFile);
					}
				} finally {
					monitor.endTask();
