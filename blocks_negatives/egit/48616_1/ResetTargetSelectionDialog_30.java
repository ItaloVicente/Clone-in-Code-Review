						RevWalk rw = new RevWalk(repo);
						RevCommit commit = rw.parseCommit(resolved);
						sha1.setText(AbbreviatedObjectId.fromObjectId(commit)
								.name());
						subject.setText(commit.getShortMessage());
						author.setText(commit.getAuthorIdent().getName()
								+ commit.getAuthorIdent().getEmailAddress()
						committer.setText(commit.getCommitterIdent().getName()
								+ commit.getCommitterIdent().getEmailAddress()
						rw.dispose();
