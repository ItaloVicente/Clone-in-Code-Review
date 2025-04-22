						try (Git git = new Git(getRepository())) {
							newHead = git.commit()
									.setMessage(srcCommit.getFullMessage())
											+ srcCommit.getShortMessage())
									.setAuthor(srcCommit.getAuthorIdent())
									.setNoVerify(true).call();
						}
