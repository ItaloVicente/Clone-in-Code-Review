						String newMessage = commitToPick.getFullMessage();
						if (isInteractive()) {
							newMessage = interactiveHandler
									.modifyCommitMessage(newMessage);
							if (newMessage == null)
								return abort(RebaseResult.ABORTED_RESULT);
						}
