			Ref head = repository.getRef(Constants.HEAD);
			if (head == null || head.getObjectId() == null)
			else {
				RevWalk rw = new RevWalk(repository);
				RevCommit commit = rw.parseCommit(head.getObjectId());

				next = CompareUtils.getFileRevisionTypedElement(gitPath,
						commit, repository);
			}
