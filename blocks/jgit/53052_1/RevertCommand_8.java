					if (!noCommit) {
						try (Git git = new Git(getRepository())) {
							newHead = git
									.commit()
									.setMessage(newMessage)
									.call();
						}
