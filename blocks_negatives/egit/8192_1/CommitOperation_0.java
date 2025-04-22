			commitCommand
					.setAuthor(
							new PersonIdent(authorIdent,
									commitDate, timeZone))
					.setCommitter(
							new PersonIdent(committerIdent,
									commitDate, timeZone))
					.setAmend(amending)
