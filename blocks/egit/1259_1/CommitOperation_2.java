							git.commit()
									.setAll(true)
									.setAuthor(
											new PersonIdent(authorIdent,
													commitDate, timeZone))
									.setCommitter(
											new PersonIdent(committerIdent,
													commitDate, timeZone))
