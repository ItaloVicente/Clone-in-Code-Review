				File messageFile = rebaseState.getFile(MESSAGE);
				if (messageFile.exists()) {
					commit.setMessage(RebaseState.readFile(messageFile));
					commit.setAuthor(parseAuthor());
				} else {
					RevCommit current = walk.parseCommit(
							repo.resolve(rebaseState.readFile(CURRENT)));
					commit.setMessage(current.getFullMessage());
					commit.setAuthor(current.getAuthorIdent());
				}
