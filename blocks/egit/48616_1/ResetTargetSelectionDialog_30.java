						try (RevWalk rw = new RevWalk(repo)) {
							RevCommit commit = rw.parseCommit(resolved);
							sha1.setText(AbbreviatedObjectId
									.fromObjectId(commit).name());
							subject.setText(commit.getShortMessage());
							author.setText(
									commit.getAuthorIdent().getName() + " <" //$NON-NLS-1$
											+ commit.getAuthorIdent()
													.getEmailAddress()
											+ "> " //$NON-NLS-1$
											+ gitDateFormatter.formatDate(
													commit.getAuthorIdent()));
							committer.setText(commit.getCommitterIdent()
									.getName()
									+ " <" //$NON-NLS-1$
									+ commit.getCommitterIdent()
											.getEmailAddress()
									+ "> " + gitDateFormatter.formatDate( //$NON-NLS-1$
											commit.getCommitterIdent()));
						}
