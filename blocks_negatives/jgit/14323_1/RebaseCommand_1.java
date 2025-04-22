						String oldMessage = commitToPick.getFullMessage();
						String newMessage = interactiveHandler
								.modifyCommitMessage(oldMessage);
						newHead = new Git(repo).commit().setMessage(newMessage)
								.setAmend(true).call();
						continue;
