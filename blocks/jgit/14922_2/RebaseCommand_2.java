					case SQUASH:
					case FIXUP:
						Ref orig_head = repo.getRef(Constants.ORIG_HEAD);
						ObjectId orig_headId = orig_head.getObjectId();

						Git.wrap(repo).reset().setMode(ResetType.SOFT)
								.setRef("HEAD~1").call();
						repo.writeOrigHead(orig_headId);

						File messageFixup = rebaseState.getFile(MESSAGE_FIXUP);
						if (step.action == Action.SQUASH) {
							if (messageFixup.exists()) {
								messageFixup.delete();
							}
						}
						if (!rebaseState.getFile(DONE).exists()
								|| rebaseState.readFile(DONE).isEmpty()) {
							throw new JGitInternalException(
						}
						File messageSquash = rebaseState
								.getFile(MESSAGE_SQUASH);
						try {
							String currSquashMessage = null;
							if (!messageSquash.exists()) {
								squashCount = 1;

								RevWalk rw = new RevWalk(repo);
								RevCommit squashInto = rw.parseCommit(repo
										.resolve(Constants.HEAD));
								rw.release();
								String contendPre = COMMIT_MESSAGE_COMMENT_PREFIX

								currSquashMessage = contendPre
										+ "The first commit's message is:\n"
										+ squashInto.getFullMessage();
								rebaseState.createFile(MESSAGE_SQUASH
										contendPre);
								if (step.action == Action.FIXUP) {
									rebaseState.createFile(MESSAGE_FIXUP
								}
							}
							if (currSquashMessage == null)
								currSquashMessage = rebaseState
									.readFile(MESSAGE_SQUASH);
							sb.setLength(0);
							squashCount++;
							sb.append("This is a combination of " + squashCount
									+ " commits\n");

							String ordinal;
							switch (squashCount % 10) {
							case 1:
								break;
							case 2:
								break;
							case 3:
								break;
							default:
								break;
							}

							if (step.action == Action.SQUASH) {
								sb.append("#\tThe ").append(squashCount)
										.append(ordinal)
										.append(" commit's message is:\n");
								sb.append(commitToPick.getFullMessage());
							} else {
								sb.append("#\tThe ")
										.append(squashCount)
										.append(ordinal)
										.append(" commit message will be skipped:\n");
								sb.append(commitToPick.getFullMessage()
										.replace(COMMIT_MESSAGE_REPLACE_SOURCE
												COMMIT_MESSAGE_REPLACE_WITH));
							}
							sb.append(currSquashMessage
									.substring(currSquashMessage.indexOf("\n") + 1));

							String contend = sb.toString();
							rebaseState.createFile(MESSAGE_SQUASH
							if (messageFixup.exists()) {
								rebaseState.createFile(MESSAGE_FIXUP
							}

						} finally {
							Step nextStep = steps.peek();
							String commitMessage = rebaseState
									.readFile(messageFixup.exists() ? MESSAGE_FIXUP
											: MESSAGE_SQUASH);
							if (nextStep != null
									&& ((nextStep.getAction() == Action.FIXUP) || (nextStep
											.getAction() == Action.SQUASH))) {
								newHead = new Git(repo).commit()
										.setMessage(commitMessage)
										.setAmend(true).call();
							} else {
								if (messageFixup.exists()) {
									newHead = new Git(repo).commit()
											.setMessage(commitMessage).setAmend(true)
											.call();
								} else {
									String newMessage = commitMessage;
									if (interactiveHandler != null) {
										newMessage = interactiveHandler
												.modifyCommitMessage(commitMessage);
									}
									if (newMessage == null)
										return abort(RebaseResult.ABORTED_RESULT);

									newHead = new Git(repo).commit()
											.setMessage(newMessage).setAmend(true)
											.call();
									}
							}
						}
						break;
